package printing_program;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Authorizator {
    private final HashMap<String, String[]> RoleHirarchy;
    private final HashMap<String, String[]> RoleGroups;

    private final String hirarchyPath;
    private final String groupsPath;

    public Authorizator(String hierarchyPath, String groupsPath) throws IOException {
        this.hirarchyPath = hierarchyPath;
        RoleHirarchy = loadAccessFile(this.hirarchyPath);

        this.groupsPath = groupsPath;
        RoleGroups = loadAccessFile(this.groupsPath);
    }

    private HashMap<String, String[]> loadAccessFile(String filePath) throws IOException {
        HashMap<String, String[]> accessControlList = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");

                String user = row[0];
                String[] accessibleMethods = Arrays.copyOfRange(row, 1, row.length);

                accessControlList.put(user, accessibleMethods);
            }
        }

        return accessControlList;
    }

    public void addRole(String role, String user) {
        String[] userRoles = RoleGroups.get(user);
        if (userRoles == null) {
            userRoles = new String[1];
            userRoles[0] = role;
        } else {
            String[] newUserRoles = new String[userRoles.length + 1];
            System.arraycopy(userRoles, 0, newUserRoles, 0, userRoles.length);
            newUserRoles[userRoles.length] = role;
            userRoles = newUserRoles;
        }
        RoleGroups.put(user, userRoles);
        setRoleGroups();
    }

    public void removeRole(String role, String user) {
        String[] userRoles = RoleGroups.get(user);
        if (userRoles == null) {
            return;
        }

        String[] newUserRoles = new String[userRoles.length - 1];
        int j = 0;
        for (String userRole : userRoles) {
            if (!userRole.equals(role)) {
                newUserRoles[j] = userRole;
                j++;
            }
        }
        RoleGroups.put(user, newUserRoles);
        setRoleGroups();
    }

    public void setRoleGroups() {
        try {
            FileWriter writer = new FileWriter(groupsPath);
            for (String user : RoleGroups.keySet()) {
                writer.write(user + "," + String.join(",", RoleGroups.get(user)) + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isAuthorized(String user, String method) {
        List<String[]> accessibleMethodsList = new ArrayList<>();
        String[] role = RoleGroups.get(user);

        for (String r :role){
            accessibleMethodsList.add(RoleHirarchy.get(r));
        }
        if (accessibleMethodsList == null) {
            return false;
        }
        String[] accessibleMethods = accessibleMethodsList.stream()
                .flatMap(Arrays::stream)
                .toArray(String[]::new);

        for (String accessibleMethod : accessibleMethods) {
            if (accessibleMethod.equals(method)) {
                return true;
            }
        }

        return false;
    }
}
