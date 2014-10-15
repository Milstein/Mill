cnv = document.getElementById('cnv')
cnv.onselectstart = function () { return false; }
ctx = cnv.getContext('2d')

// color and canvas variables
empty = '#282828'
pink = '#CC6699'
blue = '#6699FF'
k = 20 // scaling factor
m = 50 // canvas-board margin
rad = k // circle radius
cir = 2*Math.PI
line = 3
alpha = 0
steps = 15 // more steps, longer fade
info = document.getElementById('info')

// game state parameters
num_pieces = 3 // number of pieces per player

// misc.
wait = 1000
on = new Audio('on.mp3')
on.volume = .1
r_sound = new Audio('off.mp3')
r_sound.volume = .1