package org.example;

public class Directory {
    //    public void monitor() {
//        Path path = Paths.get(pathString);
//        try {
//            WatchService watcher = FileSystems.getDefault().newWatchService();
//
//            path.register(watcher,
//                    ENTRY_CREATE,
//                    ENTRY_DELETE,
//                    ENTRY_MODIFY);
//
//            WatchKey key;
//            while ((key = watcher.take()) != null) {
//                for (WatchEvent<?> event : key.pollEvents()) {
//                    System.out.println(
//                            "Event kind:" + event.kind()
//                                    + ". File affected: " + event.context() + ".");
//                }
//                key.reset();
//            }
//
//        } catch (IOException | InterruptedException x) {
//            System.err.println(Arrays.toString(x.getStackTrace()));
//        }
//    }
}
