package exception;
/**
 * 自定义异常类，用于处理无效的计时器参数
 */
public class InvalidTimerException extends Exception {
    public InvalidTimerException(String msg) {
        super(msg);

    }
}
// 示例调用代码（添加到main方法中）
//public static void main(String[] args) {
//    try {
//        // 创建信号灯时验证signalID
//        String signalID = "D";
//        if (signalID == null || signalID.trim().isEmpty()) {
//            throw new InvalidSignalIDException("Signal ID cannot be empty");
//        }
//        TrafficSignal ts4 = new TrafficSignal(signalID);
//
//        // 设置计时器时验证参数
//        int redTime = 5;
//        int yellowTime = -2; // 无效参数
//        int greenTime = 10;
//        if (redTime < 0 || yellowTime < 0 || greenTime < 0) {
//            throw new InvalidTimerException("Timer values must be positive");
//        }
//        ts4.setTimers(redTime, yellowTime, greenTime);
//
//    } catch (InvalidSignalIDException | InvalidTimerException e) {
//        System.out.println("Validation Error: " + e.getMessage());
//    } catch (Exception e) {
//        System.out.println("Unexpected error: " + e.getMessage());
//    }

    // 原有main方法的其他代码...
