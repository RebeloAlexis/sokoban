(define (problem sokoban-test) (:domain sokoban)
    (:objects 
        c-1-1 c-2-1 c-3-1 - cell
    )

    (:init
        (player-at c-1-1)
        (box-at c-2-1)
        (free c-3-1)

        (right c-1-1 c-2-1)
        (right c-2-1 c-3-1)

        (left c-2-1 c-1-1)
        (left c-3-1 c-2-1)   
    )

    (:goal 
        (and
            (box-at c-3-1)
        )
    )
)
