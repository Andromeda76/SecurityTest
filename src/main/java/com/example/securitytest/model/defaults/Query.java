package com.example.securitytest.model.defaults;



public final class Query {

    public static final Query Query_Instance;

    private Query() {}

    static {
        Query_Instance = new Query();
    }

    public static String CUSTOM_USER_BY_USERNAME_QUERY(){
        return "SELECT username, password, enabled FROM Person_Info.Person WHERE username = ?";
    }


    public static String CUSTOM_GROUP_AUTHORITIES_BY_USERNAME_QUERY() {
        return "SELECT p.username, g.group_name as group_name, a.authority_name as authority " +
                "FROM Person_Info.Person p " +
                "JOIN Person_Info.Group_Info g ON p.group_info_id = g.id " +
                "JOIN Person_Info.Authority a ON g.authority_id = a.id " +
                "WHERE p.username = ?";
    }


    public static String CUSTOM_GROUP_QUERY(){
        return "SELECT p.username, g.group_name " +
                "FROM Person_Info.Person p " +
                "JOIN Person_Info.Group_Info g ON p.group_info_id = g.id " +
                "WHERE p.username = ?";
    }

}
