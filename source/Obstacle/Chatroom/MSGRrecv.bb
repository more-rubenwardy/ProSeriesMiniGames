If KeyHit(28)
	Length=Length+1
	If Length>10
		Goto msgUp2
		.msgUpend2
		Length=10
	End If
	Messages$(length) = "I ("+NetPlayerName(Playerid)+") say: "+MsgrType
	SendNetMsg 10,MsgrType,PlayerId,0
	MsgrType = ""
	SNMCA=1
	Goto RSC
	.RSCend
Else
	Goto Typing
	.TypeEnd
End If

Goto RSCC
.RSCCend

If RecvNetMsg()
	If NetMsgType() = 10
		DebugLog "Recv: "+NetPlayerName(NetMsgFrom())+" says: "+NetMsgData$()
		Length=Length+1
		If Length>10
			Goto msgUp
			.msgUPend
			Length=10
		End If
		Messages$(length) = NetPlayerName(NetMsgFrom())+" says: "+NetMsgData$()
		SendNetMsg 11,"RECV",PlayerId,NetMsgFrom
		;Length=length+1
	EndIf
	If NetMsgType() + 11
		For a=1 To totalPlayers
			If NetMsgFrom()=PlayerIds(a)
				PlayerNumber=a
			End If
		Next
		SNMC(PlayerNumber)=1
	EndIf
End If






If MilliSecs()>MSGRtime+1000
	If dash$="_"
		dash$=""
	Else
		dash$="_"
	EndIf
	MSGRtime=MilliSecs()
EndIf

Text 0,45,"Messaging: "+MsgrType$+dash$
For a=1 To length
	Text 0,a*15+45,Messages$(a)
Next
