package com.example.nimitagg.popularmovies2;

import ckm.simple.sql_provider.UpgradeScript;
import ckm.simple.sql_provider.annotation.ProviderConfig;
import ckm.simple.sql_provider.annotation.SimpleSQLConfig;

/*
*
 * Created by Nimit Agg on 09-03-2016.

*/

@SimpleSQLConfig(
        name = "TestProvider",
        authority = "just.some.test_provider.authority",
        database = "test.db",
        version = 1)
public class TestProviderConfig implements ProviderConfig {
    @Override
    public UpgradeScript[] getUpdateScripts() {
        return new UpgradeScript[0];
    }
}
