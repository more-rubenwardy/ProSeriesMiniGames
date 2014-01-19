; MSG TYPES
; 1 setting up connection
; 2 usercount
; 3 x
; 4 y
; 5 z
; 6 command
; 10 chat

; Var MODE Values
; 0 Error
; 1 Join
; 2 Host

.Start
AppTitle "Chatroom Pro - Menu"
Graphics 640,480,0,2
img=LoadImage("ChatroomMenu.png")
DrawImage img,0,0
WaitKey()
If KeyDown(2)
	scrType=2
Else If KeyDown(3)
	scrType=1
Else
	Goto start
EndIf


Cls
Locate 0,0
Delay 100
AppTitle "Chatroom Pro - Connecting to a Game"
Mode=StartNetGame()
maxPlayers=10
Dim playerIDs(maxPlayers)
Dim playerNames$(maxPlayers)
Dim playerX(maxPlayers)
Dim playerY(maxPlayers)
Dim playerZ(maxPlayers)

;To be placed before game loop
;in setting up game

Include "MSGRsetup.bb"
AppTitle "Chatroom Pro - Type Name"
If Mode=0
	Print "Error Joining"
	Delay 1000
	Goto Start
ElseIf Mode = 1
	playerName$=Input$("What is your name? ")
	playerID=CreateNetPlayer(playerName)
	AppTitle "Chatroom Pro - Connecting..."
	Print "Waiting for reply..."
	SendNetMsg 1,"New",playerID,0
	TimeOut=MilliSecs()
	TMoutCount=1
	While Connected=0
		If RecvNetMsg()			
			DebugLog "2; Msg Recvd"
			If NetMsgData$()= "recv"
				Connected=1
				DebugLog "2; Recv "
			Else If NetMsgData$()= "started"
				Print "Game Already Started"
				Delay 1000
				Goto Start
			Else If NetMsgData$()= "tomany"
				Print "Game Reached 10 Players Limit"
				Delay 1000
				Goto Start
			Else If NetMsgType()=2
				DebugLog "1 Total Players Recvd"
				totalPlayers=NetMsgData()
			End If
		End If
		If MilliSecs()>TimeOut+2000
			TMoutCount=TMoutCount+1
			TimeOut=MilliSecs()
			If TMoutCount>10
				Print "TimedOut - Disconnecting"
				SendNetMsg 1,"T-ERR",playerID,0
				Delay 1000
				End
			Else
				Print "TimedOut - Sending Again (Try: "+TMoutCount+" / 10)"
				SendNetMsg 1,"New",playerID,0
			EndIf
		End If
	Wend
	AppTitle "Chatroom Pro - Waiting for Host"
	Print "Successfully Connected"
	Print "Please Wait... "
	While StartedGame=0
		If RecvNetMsg() Then
			msgFrom=NetMsgFrom()
			DebugLog "2; Msg Recvd"
			If NetMsgData$()="Start" And NetMsgType()=1
				DebugLog "2; Start Recvd"
				StartedGame=1
			ElseIf NetMsgType()=2
				DebugLog "1 Total Players Recvd"
				totalPlayers=NetMsgData()
			End If
		End If
	Wend
ElseIf Mode = 2
	playerName$=Input$("What is your name? ")
	playerID=CreateNetPlayer(playerName)
	AppTitle "Chatroom Pro - Getting Players"
	ipaddress$ = CountHostIPs("") 
	ipaddress$ = DottedIP$(HostIP(1)) 
	Print "Your IP address is: "+IPaddress
	Print ""
	Print "Players joined so far:"
	Print "(Press ENTER when everyone you want has joined)"
	Print ""
	totalPlayers=1
	playerIDs(totalPlayers)=playerID
	playerNames(totalPlayers)=playerName$
	playerX(totalPlayers)=0
	playerY(totalPlayers)=0
	playerZ(totalPlayers)=0
	
	While StartedGame=0
		If RecvNetMsg() Then
			msgType=NetMsgType()
			SendersId=NetMsgFrom()
			If msgType=1 Then
				If NetMsgData$()="New" 
					If totalPlayers=maxPlayers
						SendNetMsg 1,"tomany",playerID,SendersId
					Else
						totalPlayers=totalPlayers+1
						SendNetMsg 2,totalPlayers,playerID,0
						Print NetPlayerName$ (SendersId)
						;Delay 100
						SendNetMsg 1,"recv",playerID,SendersId
						DebugLog "1; Recv sent"
						playerIDs(totalPlayers)=SendersId
						playerNames(totalPlayers)=NetPlayerName$(SendersId) 
						playerX(totalPlayers)=0
						playerY(totalPlayers)=0
						playerZ(totalPlayers)=0
					End If
				ElseIf NetMsgData$()="T-ERR"
					Print NetPlayerName$(NetMsgFrom)+" has disconnected due to a Time Out Error"
				End If
			End If
		End If
		If KeyDown(28) And totalPlayers>1
			StartedGame=1
		End If
	Wend
	SendNetMsg 1,"Start",playerID,0 
End If
AppTitle "Chatroom Pro - Loading..."

;Total Players
If mode=2
	SendNetMsg 2,totalPlayers,playerID,0
Else
	While Not RecvNetMsg():Wend
	totalPlayers = NetMsgData()
EndIf

;---PlayerIDs---
AllRecv=0
If Mode=2
	For a=1 To totalPlayers
		SendNetMsg 2,PlayerIds(a),playerID,0
	Next
Else
	a=1
	While AllRecv=0
		If a>totalPlayers
			AllRecv=1
		End If
		If RecvNetMsg()
			playerIDs(a)=NetMsgData()
			playerNames$(a)=NetPlayerName(NetMsgData())
			playerX(a)=0
			playerY(a)=0
			playerZ(a)=0
			a=a+1
		End If
	Wend
End If
;---\PlayerIDs---
Const Type_Player=1,Type_Scene=2
If scrType=1
	Graphics3D 1024,768,0,1
Else
	Graphics3D 640,480,0,2
End If

cam=CreateCamera()
CameraClsColor cam,86,233,233
EntityRadius cam,10
EntityType cam,type_player
Light=CreateLight()

For a=1 To totalPlayers
	If PlayerId=PlayerIds(a)
		PlayerNumber=a
	End If
Next

Dim PlayerPositions(totalPlayers)
For a=1 To totalPlayers
	PlayerPositions(a)=CreateSphere()
	EntityType PlayerPositions(a),Type_player
	ScaleEntity PlayerPositions(a),6,6,6
	tex=CreateTexture(100,15)
	SetBuffer TextureBuffer(tex)
	ClsColor Rand(100-255),Rand(100-255),Rand(100-255)
	Cls
	Color 0,0,0
	Text 50,0,NetPlayerName$(PlayerIds(a)),True,False
	EntityTexture PlayerPositions(a),tex
	If PlayerNumber=a
		HideEntity PlayerPositions(a)
	End If	
Next 

SetBuffer BackBuffer()
; Terrain
terr=LoadTerrain ( "ground.jpg" )
ScaleEntity terr,10,90,10
PositionEntity terr,-600,-60,-750
grass=LoadTexture( "greenery.jpg" )
ScaleTexture grass, 10,10
EntityTexture terr,grass
EntityType terr,type_scene
Xupdate=MilliSecs()
Yupdate=MilliSecs()+50
Zupdate=MilliSecs()+75
	
stat$="Game Successfully Started"
statTimer=MilliSecs()
Collisions Type_player,Type_scene,2,3
AppTitle "Chatroom Pro"
;-------------------------------------------------------
;-------------------------------------------------------
;-------------------------START-------------------------
;-------------------------------------------------------
;-------------------------------------------------------
Img=LoadImage("HostControl.jpg")
While Not KeyDown(1)
	MoveEntity cam,0,-0.2,0
	For a=1 To totalPlayers
		TurnEntity PlayerPositions(a),0,-1,0
	Next
	
	;HostMSG
	If Mode = 2
		If RecvNetMsg() Then
			msgType=NetMsgType()
			Select msgType 
			Case 1
				If NetMsgData$()="New" Then
					SendNetMsg 1,"started",playerID,NetMsgFrom()
					stat$="A player named "+NetMsgData$+" tried to join but was rejected"
					statTimer=MilliSecs()
				End If
			End Select
		End If
	End If
	If RecvNetMsg() Then
		If NetMsgType() = 1
			If NetMsgData$() = "-END"
				SendNetMsg 1,"-END",playerId,0
				Cls
				Print "Game Ended"
				Delay 1000
				End
			ElseIf NetMsgData$() = PlayerId
				Cls
				Print "You have been blocked by the Host"
				Print "Leaving Game..."
				Delay 1000
				End
			End If
		End If
		Select msgType 
			Case 101
				For a=1 To totalPlayers
					If NetMsgFrom()=PlayerIds(a)
						PlayerNumber=a
					End If
				Next
				HideEntity PlayerPositions(PlayerNumber)
			Case 102
				If NetMsgData = PlayerID
					Mode = 2
				End If
				stat$="Host changed to  "+NetPlayerName$(NetMsgData)+" as the host has left"
				statTimer=MilliSecs()
			Case 200
				RuntimeError "Fatal Error. If internet is on please report the problem"
		End Select
	End If
	
	;Movement
	If KeyDown(200)=True Then MoveEntity cam,0,0,1
	If KeyDown(203)=True Then TurnEntity cam,0,1,0
	If KeyDown(205)=True Then TurnEntity cam,0,-1,0
	;MoveEntity cam,0,-0.2,0
	
	;Position Upgrade
	If MilliSecs()>Xupdate+200
		Xupdate=MilliSecs()
		SendNetMsg 3,EntityX(cam),playerID,0
	End If
	If MilliSecs()>Yupdate+200
		Yupdate=MilliSecs()
		SendNetMsg 4,EntityY(cam),playerID,0
	End If
	If MilliSecs()>Zupdate+200
		Zupdate=MilliSecs()
		SendNetMsg 5,EntityZ(cam),playerID,0
	End If
	
	If RecvNetMsg() Then
		If NetMsgType()>=3 Or NetMsgType()<=5
			Goto FindPlayerNumber 	
		End If
.Back
	End If
	
	If MilliSecs()>ststTimer+2000
		stat=""
		statTimer=MilliSecs()
	End If
	
	RenderWorld
	Color 0,0,0
	If Mode=2
		Text 0,0,"Your Name: "+playerName$+" (HOST)"
		DrawImage img,GraphicsWidth()-193,0
		If PlayerMenu=False
			If KeyDown(2)=True Then 
				If tsg<5
					SendNetMsg 1,"-END",playerId,0
					tsg=tsg+1
				Else
					StopNetGame
					Cls
					Print "Game Ended"
					Delay 1000
					End
				End If
			ElseIf KeyDown(3)
				PlayerMenu=True
				PlrMenuSlcted=0
			EndIf
		End If
	Else
		Text 0,0,"Your Name: "+playerName$
	End If
	Text 0,15,"Total Players: "+totalPlayers
	Text 0,30,stat$
	
	If PlayerMenu=True
		If PlrMenuSlcted=0
				Color 255,0,0
			Else
				Color 0,0,0
			EndIf
		Text GraphicsWidth()-193,115,"Close ["+PlrMenuSlcted+"]"
		For PlrMenu = 1 To totalPlayers
			If PlrMenuSlcted=PlrMenu
				Color 255,0,0
			Else
				Color 0,0,0
			EndIf
			Text GraphicsWidth()-193,PlrMenu*15+115,PlayerNames(PlrMenu)
			PlrMenrSav=PlrMenu
		Next
		If KeyHit(12)
			If PlrMenuSlcted>0
				PlrMenuSlcted = PlrMenuSlcted - 1
			EndIf
		ElseIf KeyHit(13)
			If PlrMenuSlcted < totalPlayers
				PlrMenuSlcted = PlrMenuSlcted + 1
			EndIf
		EndIf
		If KeyDown(14)
			If PlrMenuSlcted=0
				PlayerMenu=False
			Else
				If tsg<5
					SendNetMsg 1,PlayerIDs(PlrMenuSlcted),playerId,PlayerIDs(PlrMenySlcted)
					DebugLog "-ENDP: "+PlayerNames(PlrMenySlcted)
					tsg=tsg+1
				End If
			EndIf
		EndIf
	EndIf
	Color 0,0,0
;MSGRRECV

Include "MSGRrecv.bb"
	
;/MSGRRECV
	
	Flip
	UpdateWorld
Wend
End

.FindPlayerNumber
	For a=1 To totalPlayers
		If NetMsgFrom()=PlayerIds(a)
			PlayerNumber=a
		End If
	Next
	Select NetMsgType()
	Case 3
		PlayerX(PlayerNumber)=NetMsgData()
	Case 4
		PlayerY(PlayerNumber)=NetMsgData()			
	Case 5
		PlayerZ(PlayerNumber)=NetMsgData() 		
	End Select
	PositionEntity PlayerPositions(PlayerNumber),PlayerX(PlayerNumber),PlayerY(PlayerNumber),PlayerZ(PlayerNumber)
Goto Back



Include "MSGRfunc.bb"