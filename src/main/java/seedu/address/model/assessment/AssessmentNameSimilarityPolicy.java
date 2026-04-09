package seedu.address.model.assessment;

/**
 * Utility policy for comparing assessment names and flagging likely typo variants.
 */
public final class AssessmentNameSimilarityPolicy {
    private static final int MAX_EDIT_DISTANCE = 2;

    private AssessmentNameSimilarityPolicy() {
    }

    /**
     * Returns true if two assessment names are likely typographical variants.
     */
    public static boolean areLikelyTypos(String firstName, String secondName) {
        String firstBase = removeTrailingNumber(firstName);
        String secondBase = removeTrailingNumber(secondName);

        String firstNum = getTrailingNumber(firstName);
        String secondNum = getTrailingNumber(secondName);

        boolean bothHaveNumbers = !firstNum.isEmpty() && !secondNum.isEmpty();
        boolean sameBase = firstBase.equals(secondBase);
        boolean differentNumbers = !firstNum.equals(secondNum);

        if (bothHaveNumbers && sameBase && differentNumbers) {
            return false;
        }
        return getEditDistance(firstName, secondName) <= MAX_EDIT_DISTANCE;
    }

    private static int getEditDistance(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];
        for (int i = 0; i <= a.length(); i++) {
            for (int j = 0; j <= b.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    int replace = dp[i - 1][j - 1] + (a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1);
                    int insert = dp[i][j - 1] + 1;
                    int delete = dp[i - 1][j] + 1;
                    dp[i][j] = Math.min(replace, Math.min(insert, delete));
                }
            }
        }
        return dp[a.length()][b.length()];
    }

    private static String removeTrailingNumber(String name) {
        return name.replaceFirst("\\s+\\d+$", "").trim();
    }

    private static String getTrailingNumber(String name) {
        java.util.regex.Matcher m = java.util.regex.Pattern.compile("(\\d+)$").matcher(name.trim());
        return m.find() ? m.group(1) : "";
    }
}
