import java.util.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String route = generateRoute("RFRLR", 100);

                    int size = 0;
                    for (int j = 0; j < route.length(); j++) {
                        if (route.charAt(j) == 'R') size++;
                    }

                    System.out.printf("Считаем букву 'R': %d\r\n", size);

                    synchronized (this) {
                        if (!sizeToFreq.containsKey(size)) {
                            sizeToFreq.put(size, 0);
                        }

                        sizeToFreq.put(size, sizeToFreq.get(size) + 1);
                    }
                }
            });
            thread.start();
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.join();
        }

        Map.Entry<Integer, Integer> max = sizeToFreq.entrySet().stream().max(Map.Entry.comparingByValue()).get();
        System.out.printf("Самое частое количество повторений %d (встретилось %d раз)\r\n", max.getKey(), max.getValue());
        System.out.println("Другие размеры:");
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            if (entry == max) continue;
            System.out.printf("- %d (%d раз)\r\n", entry.getKey(), entry.getValue());
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}


