package com.ots.dpel.ep.args;

import com.ots.dpel.common.args.SearchableArguments;
import com.ots.dpel.ep.core.enums.SnapshotType;

public class StatisticsArgs implements SearchableArguments {

    private SnapshotType type;
    
    public SnapshotType getType() {
        return type;
    }
    
    public void setType(SnapshotType type) {
        this.type = type;
    }
}
