package domain;

public enum Roles {
    READ,
    WRITE,
    EXECUTE;

    public static boolean isValidRole(String role) {
        for (Roles r : values()) {
            if (r.name().equals(role)) {
                return true;
            }
        }
        return false;
    }
}
