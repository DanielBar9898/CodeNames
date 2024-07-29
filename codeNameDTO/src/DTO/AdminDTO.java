package DTO;

public class AdminDTO {
    private boolean hasAdmin;
    private String adminName;

    public AdminDTO(boolean hasAdmin, String adminName) {
        this.hasAdmin = hasAdmin;
        this.adminName = adminName;
    }

    public boolean isHasAdmin() {
        return hasAdmin;
    }

    public String getAdminName() {
        return adminName;
    }
}
