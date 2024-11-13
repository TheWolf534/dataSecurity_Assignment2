package printing_program;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class Authorizator {
    private final HashMap<String, String[]> accessControlList;

    public Authorizator(String path) throws IOException {
        accessControlList = loadAccessControlList(path);
    }

    private HashMap<String, String[]> loadAccessControlList(String filePath) throws IOException {
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

    public boolean isAuthorized(String user, String method) {
        String[] accessibleMethods = accessControlList.get(user);
        if (accessibleMethods == null) {
            return false;
        }

        for (String accessibleMethod : accessibleMethods) {
            if (accessibleMethod.equals(method)) {
                return true;
            }
        }

        return false;
    }
}
