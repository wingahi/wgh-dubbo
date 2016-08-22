echo off
set ROOT_PATH=%cd%
set pc=fmb-platform
set pf=fmb-platform\fmb-platform-finance
set pe=fmb-platform\fmb-platform-external

echo %ROOT_PATH%

call mvn clean

set JARS=fmb-parent fmb-common %pc%\fmb-platform-cache %pf%\fmb-platform-finance-client %pf%\fmb-platform-finance-persistence %pe%\fmb-platform-external-client %pe%\fmb-platform-external-persistence

for %%j in (%JARS%) do  (
	echo ------------ start install [%ROOT_PATH%\%%j] ------------
	cd %ROOT_PATH%\%%j
	call mvn clean source:jar install
	echo ------------ end install [%%j] ------------
)

pause