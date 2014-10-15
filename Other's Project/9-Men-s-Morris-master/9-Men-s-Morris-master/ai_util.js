/** Placement functions **/
function cpuPlace(state){
	var hypo = dupState(state)
	hypo.player = pink
	var plays = hypo.setLegal()
	var verts = hypo.vertices
	var table = []
	initTable(table)

	// Mill
	var pMill = placeMill(state, pink)
	table['mill'][3] = pMill

	// Block
	var opMill = placeMill(state, blue)
	table['block'][3] = opMill

	for(var i=0; i<plays.length; i++){
		var n = plays[i]
		var v = verts[n]
		v.set(pink)

		// Corner
		if(contains(corners3, n))
			table['corner'][3].push(n)
		else if(contains(corners2, n))
			table['corner'][2].push(n)

		// Potential
		var millDiff = placeMill(hypo, pink).length - pMill.length
		if(millDiff>0)
			table['potential'][millDiff].push(n)

		// Restrict
		if(state.turn>=2*num_pieces){
		}

		v.set(empty)
	}

	return table
}

function placeMill(state, player){
	var hypo = dupState(state)
	hypo.player = player
	hypo.play = 'place'
	var verts = hypo.vertices
	var plays = hypo.setLegal()
	var millP = []

	for(var i=0; i<plays.length; i++){
		var n = plays[i]
		var v = verts[n]
		v.set(player)

		if(inMill(n, hypo)){
			millP.push(n)
		}

		v.set(empty)
	}

	return millP
}

/** Removal functions**/

function cpuRemove(state){
	var plays = state.legalPlays
	var hypo = dupState(state)
	var verts = hypo.vertices
	var turn = state.turn
	var table = []
	initTable(table)
	console.log('cpuRemove: '+'initialized table')
	console.log('cpuRemove: removals to check -- ' +plays)

	for(var i=0; i<plays.length; i++){
		var n = plays[i]
		var v = verts[n]
		var before, after, diff

		v.set(empty)

		// corner
		if(contains(corners3, n))
			table['corner'][3].push(n)
		if(contains(corners2, n))
			table['corner'][2].push(n)

		// block
		if(turn<2*num_pieces){
			before = placeMill(state, blue).length
			after = placeMill(hypo, blue).length
		}
		else{
			before = moveMill(state, blue).length
			after = moveMill(hypo, blue).length
		}
		diff = before - after
		if(diff>0)
			table['block'][diff].push(n)

		// potential
		if(turn<2*num_pieces){
			before = placeMill(state, pink).length
			after = placeMill(hypo, pink).length
		}
		else{
			before = moveMill(state, pink).length
			after = moveMill(hypo, pink).length
		}
		diff = after - before
		if(diff>0)
			table['potential'][diff].push(n)

		// restrict 
		if(turn>=2*num_pieces){
			before = getMoves(state, blue).length
			after = getMoves(hypo, blue).length

			diff = before - after
			if(diff>0)
				table['restrict'][diff].push(n)
		}

		// surround
		if(turn>=2*num_pieces){
			var adj = adjacent[n]
			var surr = true

			for(var j=0; j<adj.length; j++){
				if(verts[adj[j]].clr != pink)
					surr = false
			}

			if(surr)
				table['surround'][3].push(n)
		}

		v.set(blue)
		console.log('Done checking '+n)
	}

	return table
}

/** Move functions **/

function cpuMove(state){
	var hypo = dupState(state)
	hypo.player = pink
	var pairs = getMoves(state, pink)
	var verts = hypo.vertices
	var table = []
	initTable(table)

	//Mill
	var mMill = moveMill(state, pink)
	table['mill'][3] = mMill

	//Block
	var oppMill = moveMill(state, blue)
	table['block'][3] = oppMill

	for(var i=0; i<pairs.length; i++){
		var pair = pairs[i]
		var src = pair[0]
		var dest = pair[1]
		var vsrc = verts[src]
		var vdest = verts[dest]

		vsrc.set(empty)
		vdest.set(pink)

		//Corner
		if(contains(corners3, dest))
			table['corner'][3].push(pair)
		if(contains(corners2, dest))
			table['corner'][2].push(pair)

		//Potential
		var millDiff = moveMill(hypo, pink).length - mMill.length
		if(millDiff>0)
			table['potential'][millDiff].push(pair)

		//Restrict
		var opMove = getMoves(state, blue).length - getMoves(hypo, blue).length
		if(opMove>=0)
			table['restrict'][opMove].push(pair)

		vsrc.set(pink)
		vdest.set(empty)
	}

	return table
}

function getMoves(state, player){
	var temp_py = state.player
	var temp_pl = state.play
	state.player = player
	state.play = 'move'
	var pairs = state.setLegal()

	state.player = temp_py
	state.play = temp_pl
	state.setLegal()
	
	return pairs
}

function moveMill(state, player){
	var hypo = dupState(state)
	var verts = hypo.vertices
	var pairs = getMoves(hypo, player)
	var millM = []

	for(var i=0; i<pairs.length; i++){
		var p = pairs[i]
		var v1 = verts[p[0]]
		var v2 = verts[p[1]]
		var n = v2._n

		v1.set(empty)
		v2.set(player)

		if(inMill(n, hypo))
			millM.push(p)

		v1.set(player)
		v2.set(empty)
	}

	return millM
}

/** Auxiliary functions **/

function CPUcheckMill(n, state){
	if(inMill(n, state)){
		console.log('CPU completed mill')
		state.play = 'remove'
		state.setLegal(false)
		setTimeout(function(){cpuPlay(state)}, wait)
	}
	else{
		state.next(true)
	}
}

function initTable(table){

	function initRow(arr){
		arr[0] = []
		arr[1] = []
		arr[2] = []
		arr[3] = []
		arr[4] = []
		arr[5] = []
		arr[6] = []
	}

	table['mill'] = []
	table['block'] = []
	table['potential'] = []
	table['restrict'] = []
	table['corner'] = []
	table['surround'] = []

	initRow(table['mill'])
	initRow(table['block'])
	initRow(table['potential'])
	initRow(table['restrict'])
	initRow(table['corner'])
	initRow(table['surround'])
}

function optimal(tbl){

	this.table = tbl
	var that = this

	this.play = function(m){

		function op(l){

			function list(){
				if(nonEmpty(that.table[m][3]))
					return that.table[m][3]
				if(nonEmpty(that.table[m][2]))
					return that.table[m][2]
				if(nonEmpty(that.table[m][1]))
					return that.table[m][1]
				return that.table[m][0]
			}

			if(arguments.length==0)
				return list()
			if(nonEmpty(intersection(that.table[m][3], l)))
				return intersection(that.table[m][3], l)
			if(nonEmpty(intersection(that.table[m][2], l)))
				return intersection(that.table[m][2], l)
			if(nonEmpty(intersection(that.table[m][1], l)))
				return intersection(that.table[m][1], l)
			return intersection(that.table[m][0], l)
		}

		return op
	}
}