/** Game state variables **/
var turn, player, play, mainState, vertices, cursor, busy, src, legal

/** Initialization **/
function initialize(){
	turn = 1
	player = blue
	play = 'place'

	busy = true
	cursor = null
	src = null

	drawBoard(k, m)

    vertices = [new vertex(0+m, 0+m, 0),
                new vertex(9*k+m, 0+m, 1),
                new vertex(18*k+m, 0+m, 2),
                new vertex(3*k+m, 3*k+m, 3),
                new vertex(9*k+m, 3*k+m, 4),
                new vertex(15*k+m, 3*k+m, 5),
                new vertex(6*k+m, 6*k+m, 6),
                new vertex(9*k+m, 6*k+m, 7),
                new vertex(12*k+m, 6*k+m, 8),
                new vertex(0+m, 9*k+m, 9),
                new vertex(3*k+m, 9*k+m, 10),
                new vertex(6*k+m, 9*k+m, 11),
                new vertex(12*k+m, 9*k+m, 12),
                new vertex(15*k+m, 9*k+m, 13),
                new vertex(18*k+m, 9*k+m, 14),
                new vertex(6*k+m, 12*k+m, 15),
                new vertex(9*k+m, 12*k+m, 16),
                new vertex(12*k+m, 12*k+m, 17),
                new vertex(3*k+m, 15*k+m, 18),
                new vertex(9*k+m, 15*k+m, 19),
                new vertex(15*k+m, 15*k+m, 20),
                new vertex(m, 18*k+m, 21),
                new vertex(9*k+m, 18*k+m, 22),
                new vertex(18*k+m, 18*k+m, 23)];
        
    for(var i=0; i<vertices.length; i++){
    	var v = vertices[i]
        colorVertex(v, v.clr)
    }

    mainState = new gamestate(vertices, false)
    mainState.setLegal(true)
    display('Place a piece')
    busy = false
}

/** Gamestate functions**/

function gamestate(vert, silent){
	this.vertices = vert
	this.turn = 1 
	this.player = blue
	this.play = 'place'
	this.silent = silent
	this.legalPlays = []

	var that = this

	this.setLegal = function(main){
		that.legalPlays = getLegal(that)
		if(main)
			legal = that.legalPlays
		return that.legalPlays
	}

	this.next = function(main){
		busy = true
		that.turn++
		that.player==blue ? that.player=pink : that.player=blue
		that.turn>num_pieces*2 ? that.play='move' : that.play='place'
		main ? that.setLegal(true) : that.setLegal(false)

		if(!that.silent && checkWin(that))
			return

		if(that.player==pink){
			if(main){
				display('CPU playing ...')
			}
			//console.log(that.legalPlays)
			var delay = that.play=='move' ? 1.25*wait : wait
			setTimeout(function(){cpuPlay(that)}, delay)
		}
		else{
			that.play=='place' ? display('Place a piece') : display('Select a piece')
			busy = false
		}
		console.log('Legal plays: '+that.legalPlays)
		return that.legalPlays
	}
}

function dupState(state){
	var newVert = dupVert(state.vertices)
	var newState = new gamestate(newVert)

	newState.turn = state.turn
	newState.player = state.player
	newState.play = state.play
	newState.legalPlays = state.legalPlays

	return newState
}

function checkWin(state){
	var player = state.player
	var plays = state.legalPlays
	var str = ''

	if(!nonEmpty(plays)){
		player==blue ? str='CPU wins!' : str='Player wins!'
		display(str)
		return busy=true
	}
}

function printState(state){
	var str = 'player, play, turn: '
	str += state.player
	str += ', '
	str += state.play
	str += ', ' 
	str += state.turn
	console.log(str)
}

/** Legality functions **/

function getLegal(state){
	var player = state.player

	switch(state.play){
		case 'remove':
			return legal_remove(state)
		case 'place':
			return legal_place(state)
		case 'move':
			return legal_moves(state, player)

	}
}

function legal_place(state){
	var plays = []
	var verts = state.vertices

	for(var i=0; i<verts.length; i++){
		if(verts[i].clr == empty){
			plays.push(i)
		}
	}
	return plays
}

function legal_remove(state){
	var plays = []
	var player = state.player
	var verts = state.vertices

	for(var i=0; i<verts.length; i++){
		var v = verts[i]
		var c = v.clr
		if(c!=player && c!=empty && !inMill(v._n, state)){
			plays.push(i)
		}
	}

	return plays
}

function legal_moves(state, player){
	var moves = []
	var verts = state.vertices

	for(var i=0; i<verts.length;i++){
		var src = verts[i]._n

		if(verts[src].clr == player){
			var adj = adjacent[src]

			for(var j=0; j<adj.length; j++){
				if(verts[adj[j]].clr == empty)
					moves.push([src, adj[j]])
			}
		}
	}

	return moves
}

/** Player functions **/

function userPlay(hit){

	function checkMill(){
		if(inMill(hit._n, mainState)){
			console.log('Player completed mill')
			mainState.play = 'remove'
			display('Mill complete! Remove a piece')
			mainState.setLegal(true)
			checkWin(mainState)
			busy = false
		} else {
			busy = true
			mainState.next(true)
		}
	}

	switch(mainState.play){

		case 'place':
			fadeVertex(hit, blue).done(checkMill)
			return

		case 'move':
			if(src==null){
				hit.dot()
				display('Select an adjacent position')
				src = hit
				mainState.setLegal(true)
				busy = false
			}
			else if(src==hit){
				hit.clear()
				display('Select a piece')
				src = null
				mainState.setLegal(true)
				busy = false
			}
			else{
				function fun(){
					fadeVertex(hit, blue)
				}
				fadeVertex(src,empty).done(fun).done(checkMill)
				src = null
			}
			return

		case 'remove':
			r_sound.play()
			fadeVertex(hit, empty).done(function(){mainState.next(true)})
			return
	}
}

/** Event listeners **/

cnv.addEventListener('mousemove', function(e){

	if(busy)
		return

    var x = e.pageX - cnv.offsetLeft
    var y = e.pageY - cnv.offsetTop
    var hit = clickHit(x, y)
    var cand

    if(hit==null){
    	if(cursor!=null && cursor!=src){
    		cursor.clear()
    	}
    	cursor = null
    	return
    }	

    if(hit==cursor)
    	return

    var n = hit._n
   	var play = mainState.play

   	if(play=='move' && src==null)
   		cand = contains(legal, n, 0)
   	else if(play=='move')
   		cand = contains(legal, n, 1)
   	else
   		cand = contains(legal, n)

    if(cand){
    	if(cursor!=null && cursor!=src){
    		cursor.clear()
    	}
    	hit.circle()
    	cursor = hit
    }

}, false)

cnv.addEventListener('click', function(e){

	if(busy)
		return
	busy = true

    var x = e.pageX - cnv.offsetLeft
    var y = e.pageY - cnv.offsetTop
    var hit = clickHit(x, y)

    if(hit==null || hit!= cursor){
    	busy = false
    	return
    }

    cursor = null
    userPlay(hit)

}, false)

/** Listener helpers **/

function clickHit(x, y){
	var hit = null

    for(var i=0; i<vertices.length; i++){
    	var v = vertices[i]
        var vx = v._x
        var vy = v._y
        var diff = rad
        var dist = Math.sqrt(Math.pow(x-vx,2) + Math.pow(y-vy,2))
        if(dist <= diff){
            hit = v 
            break          
        }
    }

	return hit
}