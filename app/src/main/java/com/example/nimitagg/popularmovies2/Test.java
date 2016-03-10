package com.example.nimitagg.popularmovies2;

import ckm.simple.sql_provider.annotation.SimpleSQLColumn;
import ckm.simple.sql_provider.annotation.SimpleSQLTable;

/**
 * Created by Nimit Agg on 09-03-2016.
 */
@SimpleSQLTable(table = "test", provider = "TestProvider")
public class Test {

    @SimpleSQLColumn(value = "Title",primary = true)
    public String title;

    @SimpleSQLColumn("Poster")
    public String poster;

    @SimpleSQLColumn("Overview")
    public String overview;

    @SimpleSQLColumn("Rating")
    public String rating;

    @SimpleSQLColumn("Release")
    public String release;
 //  , user rating, release date
}
