/** Vertex definitions and functions **/

function vertex(x, y, n){
    this.clr = empty
    this._x = x
    this._y = y
    this._n = n

    var that = this

    this.set = function(c){
        that.clr = c
    }

    this.clear = function(c){
        colorVertex(that, that.clr)
    }

    this.circle = function(){
        circle(that)
    }

    this.dot = function(){
        dot(that)
    }

    this.fade = function(c){
        if(c!=empty)
            on.play()
        fadeVertex(that, c)
        //that.clr = c
    }
}

function circle(v){
    var x = v._x
    var y = v._y
    ctx.beginPath()
    ctx.arc(x, y, .2*k, 0, cir)
    ctx.lineWidth = 1.5
    ctx.stroke()
}

function dot(v){
    var x = v._x
    var y = v._y
    ctx.beginPath()
    ctx.arc(x, y, .2*k, 0, cir)
    ctx.fillStyle = '#FFFFFF'
    ctx.fill()
}

function colorVertex(v, c){
    var x = v._x
    var y = v._y

    ctx.beginPath()
    ctx.arc(x, y, rad, 0, cir)
    ctx.closePath()
    ctx.fillStyle = c
    ctx.fill()

    ctx.beginPath()
    ctx.arc(x, y, rad, 0, cir)
    ctx.closePath()
    ctx.lineWidth = line
    ctx.stroke()
}

function clearVertex(v){
    var x = v._x
    var y = v._y

    ctx.beginPath()
    ctx.arc(x, y, rad, 0, cir)
    ctx.closePath()
    ctx.fillStyle = c
    ctx.fill()
}

function fadeVertex(v, c){

    if(c!=empty)
        on.play()

    busy = true
    var def = $.Deferred()

    var x = v._x
    var y = v._y
    var init = v.clr
    var fnl = c
    var i = 0
    var init_color = (init.substring(1)).match(/.{1,2}/g)
    var fnl_color = (fnl.substring(1)).match(/.{1,2}/g)

    v.clr = c

    var init_r = parseInt(init_color[0], 16)
    var init_g = parseInt(init_color[1], 16)
    var init_b = parseInt(init_color[2], 16)
    var fnl_r = parseInt(fnl_color[0], 16)
    var fnl_g = parseInt(fnl_color[1], 16)
    var fnl_b = parseInt(fnl_color[2], 16)

    dr = (fnl_r - init_r)/steps
    dg = (fnl_g - init_g)/steps
    db = (fnl_b - init_b)/steps

    //busy = true // block activity during fade-in

    interval = setInterval(function(){
        var temp = getHex(Math.round(init_r + dr*i), 
                        Math.round(init_g + dg*i), 
                        Math.round(init_b + db*i))

        ctx.beginPath()
        ctx.arc(x, y, rad-line+1, 0, cir)
        ctx.closePath()
        ctx.fillStyle = temp
        ctx.fill()

        i++
        if(i == steps){
            clearInterval(interval)
            ctx.fillStyle = c
            ctx.fill()
            def.resolve()
        }
    }, 30);

    return def 
}

/** Vertex gameplay functions **/

function isAdjacent(n1, n2){

    for(var i=0; i<adjacent.length; i++){
        if(contains(adjacent[i], n1) && contains(adjacent[i], n2))
            return true
    }
    return false
}

function inMill(n, state){
    var verts = state.vertices
    var candidates = []

    for(var i=0; i<mills.length; i++){
        if(contains(mills[i], n))
            candidates.push(mills[i])
    }

    for(var i=0; i<candidates.length; i++){
        var c1 = verts[candidates[i][0]].clr
        var c2 = verts[candidates[i][1]].clr
        var c3 = verts[candidates[i][2]].clr

        if(c1!=empty && c1==c2 && c2==c3){
            //console.log('mill: ('+n+') ' + candidates[i][0] +','+ candidates[i][1]+',' + candidates[i][2])
            return true
        }
    }

    return false
}

/** Board functions **/ 

function drawBoard(k, m){
    ctx.moveTo(0,0)
    ctx.rect(0+m, 0+m, 18*k,18*k)
    ctx.rect(3*k+m, 3*k+m, 12*k,12*k)
    ctx.rect(6*k+m, 6*k+m, 6*k,6*k)
        
    ctx.moveTo(0+m, 0+m)
    ctx.lineTo(6*k+m, 6*k+m)
    ctx.moveTo(18*k+m, 0+m)
    ctx.lineTo(12*k+m, 6*k+m)
    ctx.moveTo(0+m, 18*k+m)
    ctx.lineTo(6*k+m, 12*k+m)
    ctx.moveTo(18*k+m, 18*k+m)
    ctx.lineTo(12*k+m, 12*k+m)
        
    ctx.moveTo(9*k+m, 0+m)
    ctx.lineTo(9*k+m, 6*k+m)
    ctx.moveTo(9*k+m, 12*k+m)
    ctx.lineTo(9*k+m, 18*k+m)
    ctx.moveTo(0+m, 9*k+m)
    ctx.lineTo(6*k+m, 9*k+m)
    ctx.moveTo(12*k+m, 9*k+m)
    ctx.lineTo(18*k+m, 9*k+m)
        
    ctx.strokeStyle = "white"
    ctx.lineWidth = line
    ctx.stroke()
}

function dupVert(v){
    var dup = []

    for(var i=0; i<v.length; i++){
        var oldV = v[i]
        var newV = new vertex(oldV._x, oldV._y, oldV._n)
        newV.clr = oldV.clr
        dup.push(newV)
    }

    return dup
}

/** Board values **/

var mills = [[0,1,2],
            [3,4,5],
            [6,7,8],
            [9,10,11],
            [12,13,14],
            [15,16,17],
            [18,19,20],
            [21,22,23],
            [0,9,21],
            [3,10,18],
            [6,11,15],
            [1,4,7],
            [16,19,22],
            [8,12,17],
            [5,13,20],
            [2,14,23],
            [0,3,6],
            [2,5,8],
            [15,18,21],
            [17,20,23]]

var adjacent = [[1,3,9], //0
                [0,4,2],
                [1,5,14],
                [0,10,6,4],
                [3,1,7,5],
                [4,2,8,13], //5
                [3,7,11],
                [6,4,8],
                [7,5,12],
                [0,10,21],
                [9,3,11,18], //10
                [6,10,15],
                [8,13,17],
                [5,12,20,14],
                [13,2,23],
                [11,18,16], //15
                [15,19,17],
                [16,12,20],
                [21,10,19,15],
                [18,16,22,20],
                [19,17,13,23], //20
                [9,18,22],
                [21,19,23],
                [22,20,14]]

var corners3 = [3, 5, 18, 20]
var corners2 = [0, 2, 21, 23, 6, 8, 15, 17]