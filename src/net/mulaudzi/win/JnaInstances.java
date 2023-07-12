package net.mulaudzi.win;

import com.sun.jna.*;
import com.sun.jna.platform.win32.*;
import com.sun.jna.platform.win32.User32;

public class JnaInstances {
	
	public static void main(String args[]) {
	    setFocusToWindowsApp("PC-Reader", 0);

	    System.exit(0);
	}

	public static void setFocusToWindowsApp(String applicationTitle, int windowState) {
	    int state = windowState;
	        switch (state) {
	        default:
	        case 0:
	            state = User32.SW_SHOWNORMAL;
	            break;
	        case 1:
	            state = User32.SW_SHOWMAXIMIZED;
	            break;
	        case 2:
	            state = User32.SW_SHOWMINIMIZED;
	            break;
	    }

	    User32 user32 = User32.INSTANCE;
	    WinDef.HWND hWnd = user32.FindWindow(null, applicationTitle);
	    if (user32.IsWindowVisible(hWnd)) {
	        if (state != User32.SW_SHOWMINIMIZED) {
	            user32.ShowWindow(hWnd, User32.SW_SHOWMINIMIZED);
	        }
	        user32.ShowWindow(hWnd, state);
	        user32.SetFocus(hWnd);
	    }
	}		
}