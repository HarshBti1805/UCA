// 1. THERE IS A MACHINE THAT SENDS WHEATHER UPDATES EVERY 2-3 SECONDS.  BUT THERE IS A CONNCETION PROBLEM AND SOMETIMES MACHINE SENDS A DUPLICATE MESSAGE. IT IS FINE TO PRINT A MESSAGE IF IT IS GREATER THAN 10 SECONDS.


// [1, "Hello"] , [3, "Hello"] , [8,"Bye"], [10,'Yoo'], [12, "Hello"] , [13, "Bye"] , [22,'Bye']
// Answer :: ['Yoo']

// 2.IMPLYEMENT ANOTHER FUNCTIONALITY TO PRINT THE MESSAGES THAT HAVE NO OCCURANCE OF THE SAME MESSAGE 10 SECONDS INTO THE FUTURE AND 10 SECONDS INTO PAST. 


// [1, "Hello"] , [3, "Hello"] , [8,"Bye"], [10,'Yoo'], [12, "Hello"] , [13, "Bye"] , [22,'Bye']
// Answer :: ['Yoo']

// // 1st FUNCTIONALITY
// import java.util.*;
// class Message {
//     public String msg;
//     public int timeStamp;
//     public Message(int timeStamp, String msg) {
//         this.timeStamp = timeStamp;
//         this.msg = msg;
//     }
// }
// interface MessageTracker {
//     Message getMessage();    
// }
// class MockMessageTracker implements MessageTracker {
//     private Queue<Message> messages;
//     public MockMessageTracker() {
//         messages = new LinkedList<>();
//         messages.add(new Message(1, "Hello"));
//         messages.add(new Message(2, "Hello"));
//         messages.add(new Message(8, "Bye"));
//         messages.add(new Message(10, "Yoo"));
//         messages.add(new Message(12, "Hello"));
//         messages.add(new Message(13, "Bye"));
//     }
//     @Override
//     public Message getMessage() {
//         return messages.poll();
//     }
// }
// class RobotTracker {
//     MessageTracker messageTracker;
//     private Map<String, Integer> mp; // msg, timeStamps
//     private Queue<Message> queue;
//     public RobotTracker(MessageTracker messageTracker) {
//         this.messageTracker = messageTracker;
//         this.mp = new HashMap<>();
//         this.queue = new LinkedList<>();

//     }
//     public void pollMessage() {
//         while (true) {
//             Message message = messageTracker.getMessage();
//             if (message == null) break; 
//             shouldPrintMessage(message);
//         }
//     }
//     private void shouldPrintMessage(Message message) {
//         String msg = message.msg;
//         int timeStamp = message.timeStamp;
//         if (!mp.containsKey(msg) || timeStamp - mp.get(msg) >= 10) {
//             System.out.println("[" + timeStamp + ", \"" + msg + "\"]");
//             mp.put(msg, timeStamp);
//         }
//     }
// }
// public class Main {
//     public static void main(String[] args) {
//         MessageTracker tracker = new MockMessageTracker();
//         RobotTracker robotTracker = new RobotTracker(tracker);
//         robotTracker.pollMessage();
//     }
// }


// ********************************************************************************************

// // 2ND FUNCTIONALITY 
// import java.util.*;
// class Message {
//     public String msg;
//     public int timeStamp;
//     public boolean shouldPrintMessage;
//     public Message(int timeStamp, String msg) {
//         this.timeStamp = timeStamp;
//         this.msg = msg;
//         this.shouldPrintMessage = true; 
//     }
// }
// interface MessageTracker {
//     Message getMessage();    
// }
// class MockMessageTracker implements MessageTracker {
//     private Queue<Message> messages;
//     public MockMessageTracker() {
//         messages = new LinkedList<>();
//         messages.add(new Message(1, "Hello"));
//         messages.add(new Message(3, "Hello"));
//         messages.add(new Message(8, "Bye"));
//         messages.add(new Message(10, "Yoo"));
//         messages.add(new Message(12, "Hello"));
//         messages.add(new Message(13, "Bye"));
//         messages.add(new Message(22, "Bye"));
//     }
//     @Override
//     public Message getMessage() {
//         return messages.poll();
//     }
// }
// class RobotTracker {
//     MessageTracker messageTracker;
//     private Map<String, List<Message>> messageMap; // msg -> list of messages
//     private Queue<Message> pendingQueue; // Messages waiting to be printed
//     public RobotTracker(MessageTracker messageTracker) {
//         this.messageTracker = messageTracker;
//         this.messageMap = new HashMap<>();
//         this.pendingQueue = new LinkedList<>();
//     }
//     public void pollMessage() {
//         while (true) {
//             Message message = messageTracker.getMessage();
//             if (message == null) break; 
//             shouldPrintMessageRange(message);
//         }
//         printRemainingMessages();
//     }
//     private void printTillThisTimeStamp(int thresholdTime) {
//         while (!pendingQueue.isEmpty() && pendingQueue.peek().timeStamp <= thresholdTime) {
//             Message message = pendingQueue.poll();
//             if (message.shouldPrintMessage) {
//                 System.out.println("[" + message.timeStamp + ", \"" + message.msg + "\"]");
//             }
//         }
//     }
//     private void printRemainingMessages() {
//         while (!pendingQueue.isEmpty()) {
//             Message message = pendingQueue.poll();
//             if (message.shouldPrintMessage) {
//                 System.out.println("[" + message.timeStamp + ", \"" + message.msg + "\"]");
//             }
//         }
//     }
//     private void shouldPrintMessageRange(Message currentMessage) {
//         String msg = currentMessage.msg;
//         int currentTime = currentMessage.timeStamp;
//         if (messageMap.containsKey(msg)) {
//             List<Message> previousMessages = messageMap.get(msg);
//             for (Message prevMessage : previousMessages) {
//                 int timeDiff = Math.abs(currentTime - prevMessage.timeStamp);
//                 if (timeDiff <= 10) {
//                     prevMessage.shouldPrintMessage = false;
//                     currentMessage.shouldPrintMessage = false;
//                 }
//             }
//             previousMessages.add(currentMessage);
//         } else {
//             List<Message> messageList = new ArrayList<>();
//             messageList.add(currentMessage);
//             messageMap.put(msg, messageList);
//         }    
//         pendingQueue.offer(currentMessage);
//         printTillThisTimeStamp(currentTime - 10);
//     }
// }
// public class Main {
//     public static void main(String[] args) {
//         MessageTracker tracker = new MockMessageTracker();
//         RobotTracker robotTracker = new RobotTracker(tracker);
//         robotTracker.pollMessage();
//     }
// }

