var customNavTree10 = WpNavBar.readTree({
"childArray" : [
{   sTitle:'Home',
    bIsWebPath:true,
    sUrl:'index.html'
},
{   sTitle:'General',
    sUrl:'',
    sTarget:'_self',"childArray" : [
    {   sDescription:'Help on using the Main Menu',
        sTitle:'Main Menu',
        bIsWebPath:true,
        sUrl:'mainmenu.html'
    }]
},
{   sTitle:'Simulations',
    sUrl:'',
    sTarget:'_self',"childArray" : [
    {   sTitle:'Flying Pro',
        bIsWebPath:true,
        sUrl:'flying.html'
    },
    {   sTitle:'Dog Fighting Pro',
        bIsWebPath:true,
        sUrl:'dogfight.html',
        sTarget:'_self'
    },
    {   sTitle:'Racing Pro',
        bIsWebPath:true,
        sUrl:'racing.html'
    }]
},
{   sTitle:'Shooting',
    sUrl:'',
    sTarget:'_self',"childArray" : [
    {   sTitle:'Shooting Pro',
        bIsWebPath:true,
        sUrl:'shooting.html'
    },
    {   sTitle:'Dog Fighting Pro',
        bIsWebPath:true,
        sUrl:'dogfight.html',
        sTarget:'_self'
    }]
},
{   sTitle:'Mazes',
    sUrl:'',
    sTarget:'_self',"childArray" : [
    {   sTitle:'Obstacle Pro',
        bIsWebPath:true,
        sUrl:'obstacle.html',
        sTarget:'_self'
    },
    {   sTitle:'Amazing Pro',
        bIsWebPath:true,
        sUrl:'amazing.html',
        sTarget:'_self'
    }]
},
{   sTitle:'Action',
    sUrl:'',
    sTarget:'_self',"childArray" : [
    {   sTitle:'Jumping Pro',
        bIsWebPath:true,
        sUrl:'jumping.html',
        sTarget:'_self'
    },
    {   sTitle:'Hide and Seek Pro',
        bIsWebPath:true,
        sUrl:'hidenseek.html',
        sTarget:'_self'
    }]
}]
});