package com.ots.dpel.system.services;

public interface TestDataService {
    
    void createVoters(Integer count);
    
    void createVoters(Integer count, Integer chunksize);
    
    void undoVotes(Integer count);
    
    void undoVotes(Integer count, Integer chunksize);
}
