;To be placed at the absolute end

.msgUp
t$=Messages(9)
Messages(9)=Messages(10)

t2$=Messages(8)
Messages(8)=t

t$=Messages(7)
Messages(7)=t2

t2=Messages(6)
Messages(6)=t

t=Messages(5)
Messages(5)=t2

t2=Messages(4)
Messages(4)=t

t=Messages(3)
Messages(3)=t2

t2=Messages(2)
Messages(2)=t

Messages(1)=t2

Goto msgUPend

.msgUp2
t$=Messages(9)
Messages(9)=Messages(10)

t2$=Messages(8)
Messages(8)=t

t$=Messages(7)
Messages(7)=t2

t2=Messages(6)
Messages(6)=t

t=Messages(5)
Messages(5)=t2

t2=Messages(4)
Messages(4)=t

t=Messages(3)
Messages(3)=t2

t2=Messages(2)
Messages(2)=t

Messages(1)=t2

Goto msgUPend2

.Typing

key=GetKey()
If key<>0
	If key>80 Or key=32
		MsgrType$=MsgrType$+Chr(key)
	End If
EndIf
	
Goto TypeEnd

.RSC
For a=1 To totalplayers
	SNMC(a) = 0
Next
Goto RSCend

.RSCC
For a=1 To totalPlayers
	If SNMC(a) = 1
		b=b+1
	EndIf
Next
If b=totalPlayers
	SNMCA=0
EndIf
Goto RSCCend