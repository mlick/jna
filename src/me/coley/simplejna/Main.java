package me.coley.simplejna;

import com.sun.jna.platform.win32.WinDef;
import me.coley.simplejna.hook.key.KeyEventReceiver;
import me.coley.simplejna.hook.key.KeyHookManager;
import me.coley.simplejna.hook.mouse.MouseEventReceiver;
import me.coley.simplejna.hook.mouse.MouseHookManager;
import me.coley.simplejna.hook.mouse.struct.MouseButtonType;

import static me.coley.simplejna.Mouse.MOUSEEVENTF_LEFTDOWN;
import static me.coley.simplejna.Mouse.MOUSEEVENTF_LEFTUP;

/**
 * @author mlick lixiangxin
 * @create 2018-07-27
 */
public class Main {

    private static volatile boolean isStop = true;//是否停止，true表示停止

    private static MouseHookManager mouseHook = new MouseHookManager();
    private static MouseEventReceiver mer = new MouseEventReceiver(mouseHook) {
        @Override
        public boolean onMousePress(MouseButtonType type, WinDef.HWND hwnd, WinDef.POINT info) {
            if (!isStop) {
                System.out.println(info.x + "," + info.y + ",");
            }
//            boolean isLeft = type == MouseButtonType.LEFT_DOWN;
//            if (isLeft) {
//                System.out.println("Left mouse button has been pressed!");
//            }
            return false;
        }

        @Override
        public boolean onMouseRelease(MouseButtonType type, WinDef.HWND hwnd, WinDef.POINT info) {
//            System.out.println("Release=>" + info.x + ":" + info.y + " MouseButtonType=>" + type);
            return false;
        }

        @Override
        public boolean onMouseScroll(boolean down, WinDef.HWND hwnd, WinDef.POINT info) {
//            System.out.println("Scroll=>" + info.x + ":" + info.y + " down=>" + down);
            return false;
        }

        @Override
        public boolean onMouseMove(WinDef.HWND hwnd, WinDef.POINT info) {
//            System.out.println("Move=>" + info.x + ":" + info.y);
            return false;
        }
    };

    private static KeyHookManager keyHook = new KeyHookManager();

    private static KeyEventReceiver ker = new KeyEventReceiver(keyHook) {
        @Override
        public boolean onKeyUpdate(SystemState sysState, PressState pressState, int time, int vkCode) {
//            System.out.println("Is pressed:" + (pressState == PressState.DOWN));
//            System.out.println("Alt down:" + (sysState == SystemState.SYSTEM));
//            System.out.println("Timestamp:" + time);
//            System.out.println("KeyCode:" + vkCode);
            if (isStop && vkCode == 83) {//S键 恢复本次事件
                isStop = false;
                return true;//拦截此次按键事件
            }
            if (!isStop && vkCode == 80) {//P键 暂停本次事件
                isStop = true;
                return true;//拦截此次按键事件
            } else if (vkCode == 27) {//Esc键 退出本程序
                System.exit(0);
            }
            return false;//拦截 输入事件
        }
    };


    public static void main(String[] args) throws InterruptedException {

        isStop = false;
        mouseHook.hook(mer);
        keyHook.hook(ker);

//        for (int i = 10; i < 1000; i += 5) {
//            if (isStop) {
//                break;
//            }
//            TimeUnit.MILLISECONDS.sleep(50);
//            Mouse.mouseToXY((int) (i * 1.2), (int) (i * 0.5));
//        }
//        while (!isStop) {
//            TimeUnit.SECONDS.sleep(2);
//            //-1639:207 打开=>载入行政区划
//            Mouse.mouseToXY(-1639, 207);
//            Mouse.mouseLeftClick(0, 0);
//
//            //-555:214 关闭=>载入行政区划
//            Mouse.mouseToXY(-555, 214);
//            Mouse.mouseLeftClick(0, 0);
//
//
//        }

    }
}
