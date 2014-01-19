; GetEnv Example 
; -------------- 
Print "Loading Computer"
Print "PROCESSOR_ARCHITECTURE: "+GetEnv$("PROCESSOR_ARCHITECTURE") 
Print "ProgramFiles: "+GetEnv$("ProgramFiles") 
Print "SystemDrive: "+GetEnv$("SystemDrive") 
Print "TEMP: "+GetEnv$("TEMP") 

WaitKey() 

