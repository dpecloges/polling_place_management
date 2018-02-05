package com.ots.dpel.auth.services.impl;

import static com.ots.dpel.auth.predicates.UserPredicates.createUserIndexPredicate;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import com.mysema.query.types.Expression;
import com.ots.dpel.auth.dto.UserDto;
import com.ots.dpel.auth.services.RoleService;
import com.ots.dpel.global.exceptions.DpValidationException;
import com.ots.dpel.global.utils.DpTextUtils;
import com.ots.dpel.global.utils.MessageSourceProvider;
import com.ots.dpel.management.services.ContributionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.ConstructorExpression;
import com.mysema.query.types.FactoryExpression;
import com.ots.dpel.auth.core.domain.Permission;
import com.ots.dpel.auth.core.domain.QUser;
import com.ots.dpel.auth.core.domain.Role;
import com.ots.dpel.auth.core.domain.User;
import com.ots.dpel.auth.dto.DpUserBasicDto;
import com.ots.dpel.auth.dto.DpUserDetailsDTO;
import com.ots.dpel.auth.dto.list.UserListDto;
import com.ots.dpel.auth.persistence.DpUserRepository;
import com.ots.dpel.auth.services.UserService;
import com.ots.dpel.common.args.SearchableArguments;
import com.ots.dpel.global.utils.UserUtils;
import com.ots.dpel.management.core.domain.QElectionCenter;
import com.ots.dpel.management.core.domain.QElectionDepartment;
import com.ots.dpel.management.dto.ElectionDepartmentInfoDto;
import com.ots.dpel.management.dto.ElectionProcedureDto;
import com.ots.dpel.management.services.ElectionDepartmentService;
import com.ots.dpel.management.services.ElectionProcedureService;
import com.ots.dpel.system.services.CacheService;

/**
 * Υλοποίηση λειτουργιών που αφορούν τους χρήστες της εφαρμογής
 */
@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager entityManager;
    
    @Autowired
    private DpUserRepository dpUserRepository;
    
    @Autowired
    private ElectionDepartmentService electionDepartmentService;
    
    @Autowired
    private ElectionProcedureService electionProcedureService;
    
    @Autowired
    private RoleService roleService;
    
    @Autowired
    private ContributionService contributionService;
    
    @Autowired
    private CacheService cacheService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private MessageSourceProvider messageSourceProvider;
    
    /**
     * Ανάκτηση στοιχείων χρήστη με βάση το username
     * @param username
     * @return Αντικείμενο {@link DpUserDetailsDTO}
     */
    @Override
    @Transactional(transactionManager = "txMgr")
    public DpUserDetailsDTO findCrUserDetails(String username) {
        
        User user = dpUserRepository.findByUsername(username);
        
        if (user == null) {
            throw new AccessDeniedException("Access to api resource is not allowed. Not specified cr user details.");
        }
        
        DpUserDetailsDTO crUserDto = this.entityToDto(user);
        
        return crUserDto;
    }
    
    @Override
    public List<DpUserBasicDto> findAllUsersBasic() {
        
        QUser user = QUser.user;
        
        JPQLQuery query = new JPAQuery(entityManager).from(user);
        
        FactoryExpression<DpUserBasicDto> expression = ConstructorExpression.create(DpUserBasicDto.class, 
            user.id, user.electionDepartmentId, user.username, 
            user.email, user.firstName, user.lastName, user.type);
        
        return query.list(expression);
    }

    /**
     * Μετατροπή αντικειμένου {@link User} σε αντικείμενο {@link DpUserDetailsDTO}
     * @param user
     * @return
     */
    private DpUserDetailsDTO entityToDto(User user) {
        
        //Δημιουργία αντικειμένου
        DpUserDetailsDTO crUserDto = new DpUserDetailsDTO();
        
        //Επωνυμία
        String fullName = UserUtils.getUserFullName(user.getFirstName(), user.getLastName(), true);
        fullName = StringUtils.trim(fullName);
        
        //Φιλτράρισμα ενεργών ρόλων χρήστη
        Collection<Role> activeCrUserRoles = user.getRoleList();
        
        List<Permission> activeCrUserPermissions = new LinkedList<Permission>();
        
        //Φιλτράρισμα ενεργών δικαιωμάτων ρόλων χρήστη
        for (Role role : activeCrUserRoles) {
            activeCrUserPermissions.addAll(role.getPermissionList());
        }
        
        //Καταχώρηση municipalityId του δημοτολογίου του χρήστη
        Long municipalRegistryMunicipalityId = null;
        
        /*if (user.getMunicipalRegistry() != null && user.getMunicipalRegistry().getId() != null) {
            municipalRegistryMunicipalityId = municipalRegistryService.getMunicipalityIdOfMunicipalRegistry(user.getMunicipalRegistry().getId());
        }*/
        
        //Αντιγραφή των στοιχείων στο αντικείμενο Dto
        crUserDto.setId(user.getId());
        crUserDto.setUsername(user.getUsername());
        crUserDto.setPassword(user.getPassword());
        crUserDto.setFullName(fullName);
    
        ElectionProcedureDto electionProcedureDto = electionProcedureService.getCurrent();
        crUserDto.setElectionProcedureId(electionProcedureDto.getId());
        crUserDto.setElectionProcedureRound(electionProcedureDto.getRound());
        
        //Ανάκτηση στοιχείων εκλογικού τμήματος χρήστη εάν υπάρχουν
        if (user.getElectionDepartmentId() != null) {
            ElectionDepartmentInfoDto electionDepartmentInfo = electionDepartmentService.getElectionDepartmentInfo(user.getElectionDepartmentId());
            if (electionDepartmentInfo != null) {
                crUserDto.setElectionCenterId(electionDepartmentInfo.getElectionCenterId());
                crUserDto.setElectionCenterName(electionDepartmentInfo.getElectionCenterName());
                crUserDto.setElectionCenterDisplayName(electionDepartmentInfo.getElectionCenterDisplayName());
                crUserDto.setElectionDepartmentId(electionDepartmentInfo.getId());
                crUserDto.setElectionDepartmentName(electionDepartmentInfo.getName());
                crUserDto.setElectionDepartmentDisplayName(electionDepartmentInfo.getElectionDepartmentDisplayName());
            }
        }
        
        // crUserDto.setRegisterOfficeId(user.getRegisterOffice() != null ? user.getRegisterOffice().getId() : null);
        // crUserDto.setRegisterOfficeName(user.getRegisterOffice() != null ? user.getRegisterOffice().getName() : null);
        // crUserDto.setMunicipalRegistryId(user.getMunicipalRegistry() != null ? user.getMunicipalRegistry().getId() : null);
        // crUserDto.setMunicipalRegistryName(user.getMunicipalRegistry() != null ? user.getMunicipalRegistry().getName() : null);
        // crUserDto.setMinisterial(ministerialUser);
        // crUserDto.setEnabled(user.getIsActive().equals(YesNoEnum.YES));
        // crUserDto.setSpecialRegisterOffice(user.getRegisterOffice() != null ? user.getRegisterOffice().getIsSpecial().equals(YesNoEnum.YES) :
        //     false);
        // crUserDto.setMunicipalRegistryMunicipalityId(municipalRegistryMunicipalityId);
        
        //Δικαιώματα
        crUserDto.setPermissionList(activeCrUserPermissions);
        
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        for (Permission program : activeCrUserPermissions) {
            authorities.add(new SimpleGrantedAuthority(program.getCode()));
        }
        
        crUserDto.setAuthorities(authorities);
        
        return crUserDto;
    }
    
    /**
     * Logout User By Username - Simply remove User Details from Cache
     * TODO - Temporary Implementation
     * @param username
     */
    public void logoutUserByUsername(String username) {
        
        //Simply remove User Details from Cache
        cacheService.removeUserFromCache(username);
    }
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public String getUserFullNameById(Long id) {
        
        //Τα entities επάνω τα οποία θα δημιουργηθεί το ερώτημα
        QUser user = QUser.user;
        
        //Δημιουργία του αντικειμένου του ερωτήματος
        JPQLQuery query = new JPAQuery(entityManager);
        
        //Δημιουργία του ερωτήματος QueryDsl
        //Ανάκτηση με βάση το id
        //Επιστρωφή του ονοματεπωνύμου
        return query.from(user)
                .where(user.id.eq(id))
                .singleResult(user.lastName.concat(" ").concat(user.firstName));
    }

    @Override
    public Page<UserListDto> getUserIndex(SearchableArguments arguments, Pageable pageable) {
        
        QUser user = QUser.user;
        QElectionDepartment electionDepartment = QElectionDepartment.electionDepartment;
        QElectionCenter electionCenter = QElectionCenter.electionCenter;
        
        JPQLQuery query = new JPAQuery(entityManager)
                .from(user)
                .leftJoin(user.electionDepartment, electionDepartment)
                .leftJoin(electionDepartment.electionCenter, electionCenter);
        
        BooleanBuilder predicate = createUserIndexPredicate(arguments);
        query.where(predicate);
        
        FactoryExpression<UserListDto> expression = getUserListDtoContstructorExpresion(user, electionDepartment, electionCenter);
        
        return this.dpUserRepository.findAll(query, expression, pageable);
    }
    
    private FactoryExpression<UserListDto> getUserListDtoContstructorExpresion(QUser user, QElectionDepartment electionDepartment, QElectionCenter electionCenter) {
        return ConstructorExpression.create(UserListDto.class,
                user.id,
                user.username,
                user.lastName,
                user.firstName,
                user.email,
                electionCenter.code,
                electionCenter.name,
                electionDepartment.code,
                electionDepartment.name
            );
    }
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public UserDto findUser(Long id) {
        
        QUser user = QUser.user;
        
        JPQLQuery query = new JPAQuery(entityManager);
        query.from(user).where(user.id.eq(id));
        
        Expression<UserDto> expression = getUserDtoConstructorExpression(user);
        
        UserDto userDto = query.singleResult(expression);
        
        //Ανάκτηση (μοναδικού) ρόλου
        userDto.setRoleId(roleService.getRoleIdByUser(userDto.getId()));
        
        return userDto;
    }
    
    private Expression<UserDto> getUserDtoConstructorExpression(QUser user) {
        return ConstructorExpression.create(UserDto.class,
                user.id,
                user.username,
                user.email,
                user.lastName,
                user.firstName,
                user.electionDepartmentId
        );
    }
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public UserDto saveUser(UserDto userDto) {
        
        User user = (userDto.getId() == null) ? new User() : dpUserRepository.findOne(userDto.getId());
        
        if(user.getId() == null) {
            
            dtoToEntity(userDto, user);
            
            user.setElectionProcedureId(electionProcedureService.getCurrent().getId());
            user.setType("USER");
            if(userDto.getRoleId() != null) {
                Role role = new Role();
                role.setId(userDto.getRoleId());
                user.getRoleList().add(role);
            }
        }
        
        //Σε νέο χρήστη ή σε αλλαγή κωδικού
        if((user.getId() == null || userDto.getWillChangePassword()) && !DpTextUtils.isEmpty(userDto.getPassword())) {
            if(DpTextUtils.isEmpty(userDto.getPasswordRepeat()) || !userDto.getPassword().equals(userDto.getPasswordRepeat())) {
                throw new DpValidationException("PASSWORDS_NOT_MATCH", messageSourceProvider.getMessage("auth.user.save.error.passwordsNotMatch"));
            }
            else {
                //Δημιουργία hashed κωδικού
                user.setPassword(passwordEncoder.encodePassword(userDto.getPassword(), null));
            }
        }
        
        validateUser(user);
        
        dpUserRepository.save(user);
        
        return findUser(user.getId());
    }
    
    private void validateUser(User user) {
        
        //Συμπληρωμένο αναγνωριστικό
        if(DpTextUtils.isEmpty(user.getUsername())) {
            throw new DpValidationException("EMPTY_USERNAME", messageSourceProvider.getMessage("auth.user.save.error.emptyUsername"));
        }
        
        //Συμπληρωμένος κωδικός
        if(DpTextUtils.isEmpty(user.getPassword())) {
            throw new DpValidationException("EMPTY_PASSWORD", messageSourceProvider.getMessage("auth.user.save.error.emptyPassword"));
        }
        
        //Συμπληρωμένο επώνυμο
        if(DpTextUtils.isEmpty(user.getLastName())) {
            throw new DpValidationException("EMPTY_LASTNAME", messageSourceProvider.getMessage("auth.user.save.error.emptyLastName"));
        }
        
        //Συμπληρωμένο όνομα
        if(DpTextUtils.isEmpty(user.getFirstName())) {
            throw new DpValidationException("EMPTY_FIRSTNAME", messageSourceProvider.getMessage("auth.user.save.error.emptyFirstName"));
        }
        
        //Έχει οριστεί ρόλος
        if(user.getRoleList().isEmpty()) {
            throw new DpValidationException("NO_ROLE", messageSourceProvider.getMessage("auth.user.save.error.noRole"));
        }
        
        //Έχει οριστεί εκλογικό τμήμα αν ο ρόλος είναι πρόεδρος ή χειριστής
        String roleCode = roleService.getCode(user.getRoleList().get(0).getId());
        if(!DpTextUtils.isEmpty(roleCode) && (roleCode.equals("committee_leader") || roleCode.equals("id_verifier")) ) {
            if(user.getElectionDepartmentId() == null) {
                throw new DpValidationException("COMMITTEE_LEADER_OR_ID_VERIFIER_WITHOUT_DEPARTMENT", messageSourceProvider.getMessage("auth.user.save.error.committeeLeaderOrIdVerifierWithoutDepartment"));
            }
        }
        
        //Σε νέο χρήστη: Δεν υπάρχει ήδη αποθηκευμένος χρήστης με το συγκεκριμένο username
        if(user.getId() == null && dpUserRepository.findByUsernameAndAndElectionProcedureId(user.getUsername(), user.getElectionProcedureId()) != null) {
            throw new DpValidationException("USERNAME_EXISTS", messageSourceProvider.getMessage("auth.user.save.error.usernameExists"));
        }
    }
    
    private void dtoToEntity(UserDto userDto, User user) {
        
        if (userDto == null) {
            return;
        }
        
        if (user == null) {
            user = new User();
        }
        
        BeanUtils.copyProperties(userDto, user, "password");
    }
    
    @Override
    @Transactional(transactionManager = "txMgr")
    public void deleteUser(Long id) {
        
        //Ένας χρήστης δεν μπορεί να διαγράψει τον εαυτό του
        if(UserUtils.getUserId().equals(id)) {
            throw new DpValidationException("IS_SELF", messageSourceProvider.getMessage("auth.user.delete.error.isSelf"));
        }
        
        
        //Δεν μπορεί να διαγραφεί χρήστης που έχει δημιουργηθεί από εθελοντή
        QUser user = QUser.user;
        JPQLQuery query = new JPAQuery(entityManager);
        String email = query.from(user).where(user.id.eq(id)).singleResult(user.email);
        
        if(contributionService.contributionWithVolunteerEmailExists(email)) {
            throw new DpValidationException("CONTRIBUTION_EXISTS", messageSourceProvider.getMessage("auth.user.delete.error.contributionExists"));
        }
        
        //Διαγραφή χρήστη
        dpUserRepository.delete(id);
    }
    
    @Override
    public Boolean electionDepartmentHasUsers(Long electionDepartmentId) {
        
        if(electionDepartmentId == null) {
            return false;
        }
        
        QUser user = QUser.user;
        JPQLQuery query = new JPAQuery(entityManager);
        return query.from(user).where(user.electionDepartmentId.eq(electionDepartmentId)).exists();
    }
}