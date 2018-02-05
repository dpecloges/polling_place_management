package com.ots.dpel.config.security;

import com.ots.dpel.auth.dto.DpUserDetailsDTO;
import com.ots.dpel.global.utils.UserUtils;
import org.springframework.stereotype.Component;

/**
 * Created by glioliou on 22/9/2016.
 */
@Component("dpAuthorizationService")
public class DpAuthorizationService {
    
//     // @Autowired @Lazy
//     // private MunicipalRecordService municipalRecordService;
//
//     /**
//      * Έλεγχος αν ο χρήστης έχει δικαίωμα στο συγκεκριμένο Δημοτολόγιο ή Ληξιαρχείο ή Υπουργείου
//      * @param municipalRegistryId, registerOfficeId
//      * @return
//      */
//     public boolean hasMrOrRoOrMinistryPermission(Long registerOfficeId, Long municipalRegistryId) {
//         return hasMinistryPermission() || hasMrPermission(municipalRegistryId) || hasRoPermission(registerOfficeId);
//     }
//
//     /**
//      * Έλεγχος αν ο χρήστης έχει δικαίωμα σε Δημοτολόγιο ή Ληξιαρχείο από τα arrays ή Υπουργείου
//      * @param municipalRegistryIds, registerOfficeIds
//      * @return
//      */
//     public boolean hasMrArrayOrRoArrayOrMinistryPermission(Long[] registerOfficeIds, Long[] municipalRegistryIds) {
//
//         if (hasMinistryPermission()) {
//             return true;
//         } else {
//             for (Long registerOfficeId : registerOfficeIds) {
//                 if (hasRoPermission(registerOfficeId)) {
//                     return true;
//                 }
//             }
//             for (Long municipalRegistryId : municipalRegistryIds) {
//                 if (hasMrPermission(municipalRegistryId)) {
//                     return true;
//                 }
//             }
//             return false;
//         }
//
//     }
//
//     /**
//      * Έλεγχος αν ο χρήστης έχει δικαίωμα στο συγκεκριμένο Δημοτολόγιο ή Υπουργείου
//      * @param municipalRegistryId
//      * @return
//      */
//     public boolean hasMrOrMinistryPermission(Long municipalRegistryId) {
//         return hasMinistryPermission() || hasMrPermission(municipalRegistryId);
//     }
//
//     /**
//      * Έλεγχος αν ο χρήστης έχει δικαιώματα στο συγκεκριμένο Ληξιαρχείο ή Υπουργείου
//      * @param registerOfficeId
//      * @return
//      */
//     public boolean hasRoOrMinistryPermission(Long registerOfficeId) {
//         return hasMinistryPermission() || hasRoPermission(registerOfficeId);
//     }
//
//     /**
//      * Έλεγχος αν ο χρήστης έχει δικαιώματα υπουργείου
//      * @return
//      */
//     public boolean hasMinistryPermission() {
//
//         DpUserDetailsDTO currentUser = UserUtils.getUser();
//         return currentUser.isMinisterial();
//
//     }
//
//     /**
//      * Έλεγχος αν ο συνδεδεμένος χρήστης έχει δικαιώματα στο συγκεκριμένο Ληξιαρχείο
//      * @param registerOfficeId
//      * @return
//      */
//     public boolean hasRoPermission(Long registerOfficeId) {
//
//         DpUserDetailsDTO currentUser = UserUtils.getUser();
//         //Εάν η δοθείσα εγγραφή έχει id Ληξιαρχείου ελέγχουμε το ληξιαρχείο του χρήστη
//         if (registerOfficeId != null) {
//             //Εάν ο χρήστης δεν έχει Ληξιαρχείο δεν έχει δικαίωμα να δει εγγραφή ληξιαρχείου, εκτός από την περίπτωση του υπουργείου
//             if (currentUser.getRegisterOfficeId() == null && !currentUser.isMinisterial()) {
//                 return false;
//             } else {
//                 return currentUser.getRegisterOfficeId().equals(registerOfficeId);
//             }
//         } else { //Διαφορετικά μπορεί να τη δει
//             return true;
//         }
//
// //        return (registerOfficeId != null) ? currentUser.getRegisterOfficeId().equals(registerOfficeId) : true;
//     }
//
//     /**
//      * Έλεγχος αν ο συνδεδεμένος χρήστης έχει δικαιώματα στο συγκεκριμένο Δημοτολόγιο
//      * @param municipalRegistryId
//      * @return
//      */
//     public boolean hasMrPermission(Long municipalRegistryId) {
//
//         DpUserDetailsDTO currentUser = UserUtils.getUser();
//
//         //Εάν η δοθείσα εγγραφή έχει id Δημοτολογίου ελέγχουμε το ληξιαρχείο του χρήστη
//         if (municipalRegistryId != null) {
//             //Εάν ο χρήστης δεν έχει Δημοτολόγιο δεν έχει δικαίωμα να δει εγγραφή δημοτολογίου, εκτός από την περίπτωση του υπουργείου
//             if (currentUser.getMunicipalRegistryId() == null && !currentUser.isMinisterial()) {
//                 return false;
//             } else {
//                 return currentUser.getMunicipalRegistryId().equals(municipalRegistryId);
//             }
//         } else { //Διαφορετικά μπορεί να τη δει
//             return true;
//         }
//
// //        return municipalRegistryId != null ? currentUser.getMunicipalRegistryId().equals(municipalRegistryId) : true;
//     }
//
//     /**
//      * Έλεγχος δικαιωμάτων προβολής στοιχείων καρτέλας πολίτη
//      * Εάν ο συνδεδεμένος χρήστης είναι χρήστης υπουργείου έχει δικαίωμα προβολής των στοιχείων του πολίτη
//      * Εάν ο συνδεδεμένος χρήστης είναι χρήστης δημοτολογίου έχει δικαίωμα προβολής των στοιχείων πολιτών του συγκεκριμένου δήμου
//      * Εάν ο συνδεδεμένος χρήστης είναι χρήστης ληξιαρχείου δεν έχει δικαιωμα προβολής των στοιχείων του πολίτη
//      * @param citizenId id του πολίτη
//      * @return
//      */
//     public boolean hasCitizenViewPermission(Long citizenId) {
//
//         DpUserDetailsDTO currentUser = UserUtils.getUser();
//
//         // Εάν ο χρήστης είναι χρήστης υπουργείου
//         if (currentUser.isMinisterial()) {
//             return true;
//         } else {
//             // Εάν ο χρήστης είναι χρήστης δημοτολογίου
//             if (currentUser.getMunicipalRegistryId() != null) {
//
//                 /*// Ανάκτηση των ενεργών δημοτικοτήτων του συγκεκριμένου πολίτη
//                 List<MunicipalRecordDTO> activeMunicipalRecords = municipalRecordService.getActiveMunicipalRecordsByCitizenId(citizenId);
//
//                 // Έλεγχος εάν υπάρχει κάποια ταύτιση
//                 for (MunicipalRecordDTO activeMunicipalRecord : activeMunicipalRecords) {
//                     if (currentUser.getMunicipalRegistryMunicipalityId().equals(activeMunicipalRecord.getMunicipalityId())) {
//                         return true;
//                     }
//                 }*/
//             }
//         }
//
//         return false;
//     }
    
}