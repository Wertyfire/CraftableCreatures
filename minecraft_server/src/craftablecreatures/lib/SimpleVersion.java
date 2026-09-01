package craftablecreatures.lib;

public class SimpleVersion implements Comparable<SimpleVersion> {
    private final int[] numbers;

    public SimpleVersion(String versionStr) {
        if (versionStr == null || versionStr.isEmpty()) {
            numbers = new int[]{0};
            return;
        }
        String[] parts = versionStr.split("\\.");
        numbers = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            try {
                numbers[i] = Integer.parseInt(parts[i].trim());
            } catch (NumberFormatException e) {
                numbers[i] = 0;
            }
        }
    }

    @Override
    public int compareTo(SimpleVersion other) {
        if (other == null) return 1;
        int maxLength = Math.max(numbers.length, other.numbers.length);
        for (int i = 0; i < maxLength; i++) {
            int num1 = (i < numbers.length) ? numbers[i] : 0;
            int num2 = (i < other.numbers.length) ? other.numbers[i] : 0;
            if (num1 != num2) return Integer.compare(num1, num2);
        }
        return 0;
    }
}