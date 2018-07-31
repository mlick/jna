package me.coley.simplejna.hook.mouse.struct;

import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinUser.HOOKPROC;

/**
 * I couldn't find a LowLevelMouseProc in the provided JNA jars.
 *
 * @author Matt
 */
public interface LowLevelMouseProc extends HOOKPROC {
    public LRESULT callback(int nCode, WPARAM wParam, MOUSEHOOKSTRUCT info);

    void mouse_event(int dwFlags, int dx, int dy, int cButtons, int dwExtraInfo);

//    void mouse_event(WinDef.DWORD dwFlags,
//                     WinDef.DWORD dx,
//                     WinDef.DWORD dy,
//                     WinDef.DWORD dwData,
//                     BaseTSD.ULONG_PTR dwExtraInfo);

}
