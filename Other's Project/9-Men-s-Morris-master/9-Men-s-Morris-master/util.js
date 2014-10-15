/** Generic array functions **/

function nonEmpty(arr){
    return arr.length != 0
}

function contains(arr, val, index){

    if(arr.length==0)
        return false

    if(typeof arr[0]=='number'){
        for(var i=0; i<arr.length; i++){
            if(arr[i]==val)
                return true
        }
    }
    else{
        for(var i=0; i<arr.length; i++){
            if(arr[i][index]==val)
                return true
        }
    }

    return false
}

function intersection(arr1, arr2){
    var intr = []

    if(arr1.length==0 || arr2.length==0)
        return intr

    for(var i=0; i<arr1.length; i++){
        if(contains(arr2, arr1[i]))
            intr.push(arr1[i])
    }
    return intr
}

function rand(arr){
    var n = arr.length
    var r = Math.floor(Math.random()*n)
    return arr[r]
}

/** Misc. generic functions **/

function getHex(r, g, b) {
    return "#" + ((1<<24) + (r<<16) + (g<<8) + b).toString(16).slice(1);
}

function pair_str(arr){
    var str = ''
    for(var i=0; i<arr.length; i++){
        str+='('
        str+=arr[i][0].toString()
        str+=','
        str+=arr[i][1].toString()
        str+=') '
    }
    return str
}

function printPairs(arr){
    var str = ''

    for(var i=0; i<arr.length; i++)
        str += '(' + arr[i][0] + ',' + arr[i][1] + ') '

    console.log('CPU legal moves: '+str)
    return str
}

function display(str){
    info.innerHTML = str
}