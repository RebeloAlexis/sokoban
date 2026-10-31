(define (problem disk-4) (:domain hanoi)
(:objects 
    d1 d2 d3 d4 - disk
    p1 p2 p3 - peg
)

(:init
    ; Les disques sont sur la tige 1, [t1 -> d4 -> d3 -> d2 -> d1]
    (on d1 d2)
    (on d2 d3)
    (on d3 d4)
    (on d4 p1)

    ; Le plus petit disque et les tiges vides sont libres
    (clear d1)
    (clear p2)
    (clear p3)

    ; Relation de taille entre les disques
    (smaller d1 d2) 
    (smaller d1 d3)
    (smaller d1 d4)

    (smaller d2 d3)
    (smaller d2 d4)

    (smaller d3 d4)

    ; Les disques peuvent être posés sur une tige vide
    (smaller d1 p1) 
    (smaller d1 p2) 
    (smaller d1 p3) 

    (smaller d2 p1) 
    (smaller d2 p2) 
    (smaller d2 p3) 

    (smaller d3 p1) 
    (smaller d3 p2) 
    (smaller d3 p3) 

    (smaller d4 p1) 
    (smaller d4 p2) 
    (smaller d4 p3) 
)

(:goal (and
    (on d1 d2)
    (on d2 d3)
    (on d3 d4)
    (on d4 p3)
    
))

)
