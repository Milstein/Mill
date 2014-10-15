(function(){
	
	// General util
    
    /*if(!Object.prototype.clone) {
        Object.prototype.clone = function() {
            var newObj = (this instanceof Array) ? [] : {};
            newObj.prototype = this.prototype;
            for (var i in this) {
                if (typeof this[i] == 'object') {
                    newObj[i] = this[i].clone();
                } else {
                    newObj[i] = this[i];
                }
            }
            return newObj;
        };
    }

    if (!Array.prototype.indexOf) {
		Array.prototype.indexOf = function (searchElement) {
			if (this == null) {
				throw new TypeError();
			}
			var t = Object(this);
			var len = t.length >>> 0;
			if (len === 0) {
				return -1;
			}
			var n = 0;
			if (arguments.length > 0) {
				n = Number(arguments[1]);
				if (n != n) { // shortcut for verifying if it's NaN
					n = 0;
				} else if (n != 0 && n != Infinity && n != -Infinity) {
					n = (n > 0 || -1) * Math.floor(Math.abs(n));
				}
			}
			if (n >= len) {
				return -1;
			}
			var k = n >= 0 ? n : Math.max(len - Math.abs(n), 0);
			for (; k < len; k++) {
				if (k in t && t[k] === searchElement) {
					return k;
				}
			}
			return -1;
		}
	} */

	/* Nine Mens Morris:
	 * The board consists of 24 spots where you could put 9 black and 9 white pieces.
	 * Three pieces in a straight row form a mill.
	 */

	//{ white: false, action: 'move', from: 9, to: 10, remove: 6 }
	function printMove(move) {
		var p = move.white ? 'w: ' : 'b: ';
		if(move.action === 'put') {
			p += posIds[move.to];
		} else {
			p += posIds[move.from] + '-' + posIds[move.to];
		}
		if(move.remove)
			p += ' x ' + posIds[move.remove];
		if(move.rating || move.rating == 0.0)
			p += ' (' + move.rating.toFixed(3) + ')';
		console.log(p);
	}

	var mills = [[0,1,2], [2,3,4], [4,5,6], [6,7,0], [8,9,10], [10,11,12], [12,13,14], [14,15,8], [16,17,18], 
		[18,19,20], [20,21,22], [22,23,16], [1,9,17], [3,11,19], [5,13,21], [7,15,23]];
	
	// b = border, c = center and v = vertical mill
	var millType = ['b', 'b', 'b', 'b', 'c', 'c', 'c', 'c', 'b', 'b', 'b', 'b', 'v', 'v', 'v', 'v'];
		
	var moves = [[1,7], [0,2,9], [1,3], [2,4,11], [3,5], [4,6,13], [5,7], [6,0,15], 
		[9,15], [8,10,1,17], [9,11], [10,12,3,19], [11,13], [12,14,5,21], [13,15], [14,8,7,23], 
		[23,17], [16,18,9], [17,19], [18,20,11], [19,21], [20,22,13], [21,23], [22,16,15]];
	
	var allMoves = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23];
	var posIds = ['a7', 'd7', 'g7', 'g4', 'g1', 'd1', 'a1', 'a4', 'b6', 'd6', 'f6', 'f4', 'f2', 'd2', 'b2', 'b4', 'c5', 'd5', 'e5', 'e4', 'e3', 'd3', 'c3', 'c4'];	
	var MAXSCORE = 1000000.0;
	var weights = {
		pieces: 1.0,
		crossSpots: 2,
		cornerSpots: 1.0,
		tSpots: 1.5,
		middleCornerSpots: 0.2,
		possibleMoves: 0.2,
		vMills: 3.0,
		borderMills: 2.0,
		zMills: 3.0
	};
			
	var startPos = {
		// -1 => black, 0 => empty and 1 => white
		board: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
		whiteDeck: 9,
		blackDeck: 9,
		turns: 1, // whites turn
        
        clone: function() {
            var newObj = {};
            for (var i in this) {
                if (typeof this[i].slice != 'undefined') {
                    newObj[i] = this[i].slice(0);
                } else {
                    newObj[i] = this[i];
                }
            }
            return newObj;
        },
		
		whitesTurn: function() {
			return this.turns % 2 === 1;
		},
		
		myColor: function() {
			return this.whitesTurn() ? 1 : -1;
		},

		opponentColor: function() {
			return -this.myColor();
		},

		/* A turn specified by example:
		 * Put the white piece to position 3
		 * 		{ white: true, action: 'put', to: 3 }
		 * Move the black piece form 9 to 10 and remove a white piece from 6
		 * 		{ white: false, action: 'move', from: 9, to: 10, remove: 6 }
		 */
		doTurn: function doTurn(turn) {
			// Check whose turn it is
			if(this.whitesTurn() != turn.white)
				throw "It's not " + (turn.white ? 'whites' : 'blacks') + ' turn!';
			if(this.board[turn.to] != 0)
				throw "Spot is not empty!"
			if(turn.action === 'put') {
				// Check if there are still pieces to put
				if(turn.white && this.whiteDeck <= 0 || turn.black && this.blackDeck <= 0)
					throw "There aren't any pieces left!";
				// Check if spot is empty
				this.board[turn.to] = turn.white ? 1 : -1;
				if(turn.white) {
					this.whiteDeck--;
				} else {
					this.blackDeck--;
				}
			} else if(turn.action === 'move') {
				if(this.board[turn.from] != this.myColor())
					throw "This is not your piece!";
				this.board[turn.to] = this.board[turn.from];
				this.board[turn.from] = 0;
			} else {
				throw "Unkown action!";
			}
			if(typeof turn.remove != undefined)
				this.board[turn.remove] = 0;
			this.turns++;
            this.lastTurn = turn;
		},
		
		/* Returns a list of pieces of a given color which are not part of a mill
		 * and thus can be removed.
		 */
		piecesToRemove: function(color) {
			var pieceList = [];
			for(var j = 0; j < this.board.length; j++) {
				// It can't be part of a mill
				if(this.board[j] == this.opponentColor() && !this.wouldCreateMill(j, this.opponentColor())) {
					pieceList.push(j);
				}
			}
			return pieceList;
		},
		
		/* Returns a list of all possible moves
		 */
		genTurnList: function() {
            var i, j, removeList;
			var turnList = [];
			if(this.whitesTurn() && this.whiteDeck > 0 || !this.whitesTurn() && this.blackDeck > 0) {
				for(i = 0; i < this.board.length; i++) {
					if(this.board[i] === 0) {
						if(this.wouldCreateMill(i, this.myColor())) {
							// If this move would create a mill we have to check which piece to remove
							removeList = this.piecesToRemove(this.opponentColor());
							for(j = 0; j < removeList.length; j++) {
								turnList.push({white: this.whitesTurn(), action: 'put', to: i, remove: removeList[j]});
							}
						} else {
							turnList.push({white: this.whitesTurn(), action: 'put', to: i});
						}
					}
				}
			} else {
				// Count my pieces
				var mycolor = this.myColor();
				var myPieces = 0;
				for(i = 0; i < this.board.length; i++) {
					if(this.board[i] === mycolor) myPieces++;
				}
				for(i = 0; i < this.board.length; i++) {
					if(this.board[i] == mycolor) { // My piece?
						// Jump if less then 4 pieses
						var possible = myPieces <= 3 ? allMoves : moves[i];
						// Lookup possible moves from moves array
						for(j = 0; j < possible.length; j++) {
							var moveTo = possible[j];
							if(this.board[moveTo] === 0) { // Empty?
								if(this.wouldCreateMill(moveTo, mycolor, i)) {
									removeList = this.piecesToRemove(this.opponentColor());
									for(var k = 0; k < removeList.length; k++) {
										turnList.push({white: this.whitesTurn(), action: 'move', from: i, to: moveTo, remove: removeList[k]});
									}
								} else {
									turnList.push({white: this.whitesTurn(), action: 'move', from: i, to: moveTo});
								}
							}
						}
					}
				}
			}
			return turnList;
		},
		
		/* Checks if a move to spot s could create a mill with the given color.
		 * color: -1 | +1
		 */
		wouldCreateMill: function (s, color, from) {
			for(var i = 0; i < mills.length; i++) {
				if(mills[i].indexOf(s) >= 0 && mills[i].indexOf(from) == -1) {
					var mill = true;
					for(var j = 0; j < mills[i].length; j++) {
						var p = mills[i][j];
						if(p != s && this.board[p] != color)
							mill = false;
					}
					if(mill) return true;
				}
			}
			return false;
		},
		
		/* Evaluates the current postition. > 0 means good for white < 0 means good for black.
		 */
		rate: function() {
            var i, s;
			var ev = 0.0;
			var whiteStats = {
				pieces: 0,				// Pieces on board
				crossSpots: 0,   		// Pieces on cross spots
				cornerSpots: 0,			// Pieces on corner spots
				tSpots: 0,				// Pieces on T-spots
				middleCornerSpots: 0,	// Position 8, 10, 12 or 14
				possibleMoves: 0,		// Count of possible moves
				vMills: 0,				// Vertical mills
				borderMills: 0,			// Horizontal mills at the border
				zMills: 0				// Horizontal in the middle circle
			};
            // TODO Write a clone method that works with Three.js
			var blackStats = {
				pieces: 0,				// Pieces on board
				crossSpots: 0,   		// Pieces on cross spots
				cornerSpots: 0,			// Pieces on corner spots
				tSpots: 0,				// Pieces on T-spots
				middleCornerSpots: 0,	// Position 8, 10, 12 or 14
				possibleMoves: 0,		// Count of possible moves
				vMills: 0,				// Vertical mills
				borderMills: 0,			// Horizontal mills at the border
				zMills: 0				// Horizontal in the middle circle
			};
			// Gather the statisics
			for(i = 0; i < this.board.length; i++) {
				s = null;
				if(this.board[i] == 1 ) {
					s = whiteStats;
				} else if(this.board[i] == -1 ) {
					s = blackStats;
				}
				if(s) {
					s.pieces++;
					if(moves[i].length == 4) {
						s.crossSpots++;
					} else if(moves[i].length == 3) {
						s.tSpots++;
					} else if(moves[i].length == 2) {
						s.cornerSpots++;
						if([8, 10, 12, 14].indexOf(i) > -1)
							s.middleCornerSpots++;
					}
					for(var j = 0; j < moves[i].length; j++) {
						if(this.board[moves[i][j]] == 0)
							s.possibleMoves++;
					}
				}
			}
			// If one has only 3 pieces flexibility becomes irrelevant becuase he can jump to every empty field
			if(whiteStats.pieces == 3)
				whiteStats.possibleMoves = 1;
			if(blackStats.pieces == 3)
				blackStats.possibleMoves = 1;
			// Stats for mills
			for(i = 0; i < mills.length; i++) {
				var c = this.board[mills[i][1]];
				if(c != 0 &&  c == this.board[mills[i][2]] && c == this.board[mills[i][3]]) {
					s = c == 1 ? whiteStats : blackStats;
					switch(millType[i]) {
					case 'b':
						s.borderMills++;
						break;
					case 'c':
						s.zMills++;
						break;
					case 'v':
						s.vMills++;
						break;
					}
				}
			}
			// Calculate the numerical value to rate the position
			if(this.whiteDeck == 0 || this.blackDeck == 0) {
				if(whiteStats.pieces < 3) {
					return -MAXSCORE;
				} else if(blackStats.pieces < 3) {
					return MAXSCORE;
				} else if(whiteStats.possibleMoves == 0) {
					return -MAXSCORE;
				} else if(blackStats.possibleMoves == 0) {
					return MAXSCORE;
				}
			}
			for (s in whiteStats) {
				if(typeof whiteStats[s] == 'number') {
					ev += whiteStats[s] * weights[s];
					ev -= blackStats[s] * weights[s];
				}
			}
			return ev;
		},
		
		recurse: function(level) {
			var turnList = this.genTurnList();
			for(var i = 0; i < turnList.length; i++) {
				var pos = this.clone();
				pos.doTurn(turnList[i]);
				if(level > 0) {
					var tl = pos.recurse(level - 1);
					if(tl.length > 0) {
						turnList[i]['rating'] = tl[0].rating;
					} else {
						turnList[i]['rating'] = this.whitesTurn() ? -MAXSCORE : MAXSCORE;
					}
				} else {
					turnList[i]['rating'] = pos.rate();
				}
				if(level == 3)
					printMove(turnList[i]);
			}
			turnList.sort(function(a, b) {
				return (b['rating'] - a['rating']) * (a.white ? 1.0 : -1.0);
			});
			return turnList;
		}
	};
	
	window.addEventListener('load', onLoad, false);
    
    function onLoad() {
		console.log('init');
		var currentPos = startPos.clone();
		drawBoard(currentPos);

		window.addEventListener('keydown', function keyHandler(e) {
			switch(e.keyCode) {
				case 37:
					e.preventDefault();
					break;
				case 39:
					e.preventDefault();
					break;
				case 38:
					e.preventDefault();
					break;
				case 40:
					e.preventDefault();
					break;
				case 13:
					e.preventDefault();
					var turnList = currentPos.recurse(2);
					turnList.forEach(function(turn) {
						printMove(turn);
					});
					// TODO Select a random turn if some turns have the same rating
					currentPos.doTurn(turnList[0]);
					drawBoard(currentPos);
					break;
			} // end switch(e.keyCode));
		});
			
		console.log('init done');
	}
})();