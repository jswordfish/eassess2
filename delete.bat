SET DIRECTORY_NAME="C:\fromMainLaptop\ass_codebase\codebase\target\assessment\WEB-INF\lib"
TAKEOWN /f %DIRECTORY_NAME% /r /d y
ICACLS %DIRECTORY_NAME% /grant administrators:F /t
PAUSE