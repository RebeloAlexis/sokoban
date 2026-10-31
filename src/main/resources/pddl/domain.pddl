(define (domain sokoban)

    (:requirements :strips :typing)

    (:types
        cell
    )

    (:predicates
        ; Position du joueur
        (player-at ?c - cell)

        ; Présence d'une caisse
        (box-at ?c - cell)

        ; Case sans joueur et sans caisse
        (free ?c - cell)

        ; Relations directionnelles entre les cases
        (up ?from - cell ?to - cell)
        (down ?from - cell ?to - cell)
        (left ?from - cell ?to - cell)
        (right ?from - cell ?to - cell)
    )

    ; Déplacement vers le haut
    (:action move-up
        :parameters 
        (
            ?from - cell
            ?to - cell
        )

        :precondition 
        (and
            (player-at ?from)
            (up ?from ?to)
            (free ?to)
        )

        :effect 
        (and
            (not (player-at ?from))
            (player-at ?to)
            (free ?from)
            (not (free ?to))
        )
    )

    ; Déplacement vers le bas
    (:action move-down
        :parameters 
        (
            ?from - cell
            ?to - cell
        )

        :precondition 
        (and
            (player-at ?from)
            (down ?from ?to)
            (free ?to)
        )

        :effect 
        (and
            (not (player-at ?from))
            (player-at ?to)
            (free ?from)
            (not (free ?to))
        )
    )

    ; Déplacement vers la gauche
    (:action move-left
        :parameters 
        (
            ?from - cell
            ?to - cell
        )

        :precondition 
        (and
            (player-at ?from)
            (left ?from ?to)
            (free ?to)
        )

        :effect 
        (and
            (not (player-at ?from))
            (player-at ?to)
            (free ?from)
            (not (free ?to))
        )
    )

    ; Déplacement vers la droite
    (:action move-right
        :parameters 
        (
            ?from - cell
            ?to - cell
        )

        :precondition 
        (and
            (player-at ?from)
            (right ?from ?to)
            (free ?to)
        )

        :effect 
        (and
            (not (player-at ?from))
            (player-at ?to)
            (free ?from)
            (not (free ?to))
        )
    )

    ; Pousser vers le haut
    (:action push-up
        :parameters 
        (
            ?player - cell
            ?box - cell
            ?destination - cell
        )

        :precondition 
        (and
            (player-at ?player)
            (box-at ?box)
            (up ?player ?box)
            (up ?box ?destination)
            (free ?destination)
        )

        :effect 
        (and
            (not (player-at ?player))
            (player-at ?box)

            (not (box-at ?box))
            (box-at ?destination)

            (free ?player)
            (not (free ?destination))
        )
    )

    ; Pousser vers le bas
    (:action push-down
        :parameters 
        (
            ?player - cell
            ?box - cell
            ?destination - cell
        )
        :precondition 
        (and 
            (player-at ?player)
            (box-at ?box)
            (down ?player ?box)
            (down ?box ?destination)
            (free ?destination)
        )
        :effect 
        (and 
            (not (player-at ?player))
            (player-at ?box)

            (not (box-at ?box))
            (box-at ?destination)

            (free ?player)
            (not (free ?destination))
        )
    )

    ; Pousser vers la gauche
    (:action push-left
        :parameters 
        (
            ?player - cell
            ?box - cell
            ?destination - cell
        )
        :precondition 
        (and 
            (player-at ?player)
            (box-at ?box)
            (left ?player ?box)
            (left ?box ?destination)
            (free ?destination)
        )
        :effect 
        (and 
            (not (player-at ?player))
            (player-at ?box)

            (not (box-at ?box))
            (box-at ?destination)

            (free ?player)
            (not (free ?destination))
        )
    )

    ; Pousser vers la droite
    (:action push-right
        :parameters 
        (
            ?player - cell
            ?box - cell
            ?destination - cell
        )
        :precondition 
        (and 
            (player-at ?player)
            (box-at ?box)
            (right ?player ?box)
            (right ?box ?destination)
            (free ?destination)
        )
        :effect 
        (and 
            (not (player-at ?player))
            (player-at ?box)

            (not (box-at ?box))
            (box-at ?destination)

            (free ?player)
            (not (free ?destination))
        )
    ) 
)