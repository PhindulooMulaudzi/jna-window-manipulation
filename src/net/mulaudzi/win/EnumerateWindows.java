package net.mulaudzi.win;

import static net.mulaudzi.win.EnumerateWindows.Kernel32.OpenProcess;
import static net.mulaudzi.win.EnumerateWindows.Kernel32.PROCESS_QUERY_INFORMATION;
import static net.mulaudzi.win.EnumerateWindows.Kernel32.PROCESS_VM_READ;
import static net.mulaudzi.win.EnumerateWindows.Psapi.GetModuleBaseNameW;
import static net.mulaudzi.win.EnumerateWindows.User32DLL.GetForegroundWindow;
import static net.mulaudzi.win.EnumerateWindows.User32DLL.GetWindowTextW;
import static net.mulaudzi.win.EnumerateWindows.User32DLL.GetWindowThreadProcessId;

import com.sun.jna.*;
import com.sun.jna.platform.win32.*;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.Native;
import com.sun.jna.ptr.PointerByReference;

public class EnumerateWindows {
    private static final int MAX_TITLE_LENGTH = 1024;

    public static boolean isWindowActive(String windowName) {
        char[] buffer = new char[MAX_TITLE_LENGTH * 2];
        
        GetWindowTextW(GetForegroundWindow(), buffer, MAX_TITLE_LENGTH);
        System.out.println("Active window title: " + Native.toString(buffer));
        
        if(Native.toString(buffer).contains(windowName)) {
        	return true;
        }
        return false;
    }

    static class Psapi {
        static { Native.register("psapi"); }
        public static native int GetModuleBaseNameW(Pointer hProcess, Pointer hmodule, char[] lpBaseName, int size);
    }

    static class Kernel32 {
        static { Native.register("kernel32"); }
        public static int PROCESS_QUERY_INFORMATION = 0x0400;
        public static int PROCESS_VM_READ = 0x0010;
        public static native int GetLastError();
        public static native Pointer OpenProcess(int dwDesiredAccess, boolean bInheritHandle, Pointer pointer);
    }

    static class User32DLL {
        static { Native.register("user32"); }
        public static native int GetWindowThreadProcessId(HWND hWnd, PointerByReference pref);
        public static native HWND GetForegroundWindow();
        public static native int GetWindowTextW(HWND hWnd, char[] lpString, int nMaxCount);
    }
}
