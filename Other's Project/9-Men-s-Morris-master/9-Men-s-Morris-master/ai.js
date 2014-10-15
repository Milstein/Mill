function cpuPlay(state){
	var play = state.play
	var verts = state.vertices
	var select, table, opt

	switch(play){

		case 'place':
			table = cpuPlace(state)
			opt = new optimal(table)

			var mill = opt.play('mill')
			var block = opt.play('block')
			var potential = opt.play('potential')
			var corner = opt.play('corner')
			var restrict = opt.play('restrict')

			console.log('CPU mill: '+mill())
			console.log('CPU block: '+block())
			console.log('CPU potential: '+potential())
			console.log('CPU corner: '+corner())
			console.log('CPU restrict: '+restrict())
			console.log('-----------------')

			if(nonEmpty(corner(restrict(potential(mill())))))
				select = rand(corner(restrict(potential(mill()))))
			else if(nonEmpty(corner(potential(mill()))))
				select = rand(corner(potential(mill)))
			else if(nonEmpty(restrict(potential(mill()))))
				select = rand(restrict(potential(mill())))
			else if(nonEmpty(potential(mill())))
				select = rand(potential(mill()))
			else if(nonEmpty(restrict(corner(mill()))))
				select = rand(restrict(corner(mill())))
			else if(nonEmpty(corner(mill())))
				select = rand(corner(mill()))
			else if(nonEmpty(restrict(mill())))
				select = rand(restrict(mill()))
			else if (nonEmpty(mill()))
				select = rand(mill())
			else if(nonEmpty(restrict(corner(potential(block())))))
				select = rand(restrict(corner(potential(block()))))
			else if(nonEmpty(corner(potential(block()))))
				select = rand(corner(potential(block())))
			else if(nonEmpty(restrict(potential(block()))))
				select = rand(restrict(potential(block())))
			else if(nonEmpty(potential(block())))
				select = rand(potential(block()))
			else if(nonEmpty(restrict(corner(block()))))
				select = rand(restrict(corner(block())))
			else if(nonEmpty(corner(block())))
				select = rand(corner(block()))
			else if(nonEmpty(restrict(block())))
				select = rand(restrict(block()))
			else if(nonEmpty(block()))
				select = rand(block())
			else if(nonEmpty(restrict(corner(potential()))))
				select = rand(restrict(corner(potential())))
			else if(nonEmpty(corner(potential())))
				select = rand(corner(potential()))
			else if(nonEmpty(restrict(potential())))
				select = rand(restrict(potential()))
			else if(nonEmpty(potential()))
				select = rand(potential())
			else if(nonEmpty(restrict(corner())))
				select = rand(restrict(corner()))
			else if(nonEmpty(corner()))
				select = rand(corner())
			else if(nonEmpty(restrict()))
				select = rand(restrict())
			else
				select = rand(state.legalPlays)
			
			
			var v = verts[select]
			fadeVertex(v, pink).done(function(){CPUcheckMill(select, state)})
			return

		case 'move':
			table = cpuMove(state)
			opt = new optimal(table)

			var mill = opt.play('mill')
			var block = opt.play('block')
			var potential = opt.play('potential')
			var restrict = opt.play('restrict')
			var corner = opt.play('corner')

			if(nonEmpty(corner(restrict(potential(mill())))))
				select = rand(corner(restrict(potential(mill()))))
			else if(nonEmpty(restrict(potential(mill()))))
				select = rand(restrict(potential(mill())))
			else if(nonEmpty(corner(potential(mill()))))
				select = rand(corner(potential(mill())))
			else if(nonEmpty(potential(mill())))
				select = rand(potential(mill()))
			else if(nonEmpty(corner(restrict(mill()))))
				select = rand(corner(restrict(mill())))
			else if(nonEmpty(restrict(mill())))
				select = rand(restrict(mill()))
			else if(nonEmpty(corner(mill())))
				select = rand(corner(mill()))
			else if(nonEmpty(mill()))
				select = rand(mill())
			else if(nonEmpty(corner(restrict(potential(block())))))
				select = rand(corner(restrict(potential(block()))))
			else if(nonEmpty(restrict(potential(block()))))
				select = rand(restrict(potential(block())))
			else if(nonEmpty(corner(potential(block()))))
				select = rand(corner(potential(block())))
			else if(nonEmpty(potential(block())))
				select = rand(potential(block()))
			else if(nonEmpty(corner(restrict(block()))))
				select = rand(corner(restrict(block())))
			else if(nonEmpty(restrict(block())))
				select = rand(restrict(block()))
			else if(nonEmpty(corner(block())))
				select = rand(corner(block()))
			else if(nonEmpty(block()))
				select = rand(block())
			else if(nonEmpty(corner(restrict(potential()))))
				select = rand(corner(restrict(potential())))
			else if(nonEmpty(restrict(potential())))
				select = rand(restrict(potential()))
			else if(nonEmpty(corner(potential())))
				select = rand(corner(potential()))
			else if(nonEmpty(potential()))
				select = rand(potential())
			else if(nonEmpty(restrict()))
				select = rand(restrict())
			else if(nonEmpty(corner()))
				select = rand(corner())
			else
				select = rand(getMoves(state, pink))

			function fun1(){
				fadeVertex(dest, pink)
			}
			function fun2(){
				CPUcheckMill(select[1],state)
			}
			
			var src = verts[select[0]]
			var dest = verts[select[1]]
			fadeVertex(src, empty).done(fun1).done(fun2)
			return

		case 'remove':
			console.log('in remove case ...')
			table = cpuRemove(state)
			console.log('populated table ...')
			opt = new optimal(table)
			console.log('created opt function ...')

			var block = opt.play('block')
			var potential = opt.play('potential')
			var restrict = opt.play('restrict')
			var corner = opt.play('corner')
			var surround = opt.play('surround')

			console.log('Legal CPU removals: '+state.legalPlays)

			console.log('Removal (block): '+block())
			console.log('Removal (potential): '+potential())
			console.log('Removal (restrict): '+restrict())
			console.log('Removal (corner): '+corner())
			console.log('Removal (surround): '+surround())

			if(nonEmpty(corner(restrict(potential(block()))))){
				console.log('select: corner restrict potential block')
				select = rand(corner(restrict(potential(block()))))
			}
			else if(nonEmpty(restrict(potential(block())))){
				console.log('select: restrict potential block')
				select = rand(restrict(potential(block())))
			}
			else if(nonEmpty(corner(potential(block())))){
				console.log('corner potential block')
				select = rand(corner(potential(block())))
			}
			else if(nonEmpty(potential(block()))){
				console.log('potential block')
				select = rand(potential(block()))
			}
			else if(nonEmpty(restrict(block()))){
				console.log('restrict block')
				select = rand(restrict(block()))
			}
			else if(nonEmpty(corner(block()))){
				console.log('corner block')
				select = rand(corner(block()))
			}
			else if(nonEmpty(block())){
				console.log('block')
				select = rand(block())
			}
			else if(nonEmpty(potential(surround()))){
				console.log('potential surround')
				select = rand(potential(surround()))
			}
			else if(nonEmpty(restrict(potential()))){
				console.log('restrict potential')
				select = rand(restrict(potential()))
			}
			else if(nonEmpty(corner(potential()))){
				console.log('corner potential')
				select = rand(corner(potential()))
			}
			else if(nonEmpty(potential())){
				console.log('potential')
				select = rand(potential())
			}
			else if(nonEmpty(corner(restrict()))){
				console.log('corner restrict')
				select = rand(corner(restrict()))
			}
			else if(nonEmpty(restrict())){
				console.log('restrict')
				select = rand(restrict())
			}
			else if(nonEmpty(corner())){
				console.log('corner')
				select = rand(corner())
			}
			else{
				console.log('random')
				select = rand(state.legalPlays)
			}

			console.log('CPU removes: '+select)
			var v = verts[select]
			r_sound.play()
			fadeVertex(v, empty).done(function(){state.next(true)})
			return
	}
}