End

Function SaveGame(file$,cam,player)
	DebugLog "SAVER"
	If file$<>""
		filein=WriteFile(file$)
		If cam
			WriteLine(filein,EntityX(cam))
			WriteLine(filein,EntityY(cam))
			WriteLine(filein,EntityZ(cam))
			DebugLog "	CAMERA: Saved "+EntityX(cam)+"/"+EntityY(cam)+"/"+EntityZ(cam)
		Else
			WriteLine(filein,0)
			WriteLine(filein,0)
			WriteLine(filein,0)
			DebugLog "	CAMERA: -ERR"
		EndIf
			WriteLine(filein,EntityX(player))
			WriteLine(filein,EntityY(player))
			WriteLine(filein,EntityZ(player))
			DebugLog "	PLAYER: Saved"
		CloseFile(filein)
		DebugLog "	writen to "+file$
	EndIf
End Function

Function LoadGame(file$);,cam,player)
	DebugLog "LOADER"
	player=CreateSphere()
	If file$<>""
		filein=ReadFile(file$)
		If cam
			cx=ReadLine(filein)
			cy=ReadLine(filein)
			cz=ReadLine(filein)
		Else
			x=ReadLine(filein)
			y=ReadLine(filein)
			z=ReadLine(filein)
			DebugLog "	CAMERA: -ERR"
		EndIf
		If player
			x=ReadLine(filein)
			y=ReadLine(filein)
			z=ReadLine(filein)
			PositionEntity player,x,y,z
			DebugLog "	PLAYER: Moved "+x+"/"+y+"/"+z
		Else
			x=ReadLine(filein)
			y=ReadLine(filein)
			z=ReadLine(filein)
			DebugLog "	PLAYER: -ERR"
		EndIf
		DebugLog "	CAMERA: Moved "+cx+"/"+cy+"/"+cz
		CloseFile(filein)
		DebugLog "	loaded from "+file$
		Return player
	EndIf
End Function