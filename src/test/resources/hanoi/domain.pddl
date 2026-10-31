;Header and description

(define (domain hanoi)

;remove requirements that are not needed
(:requirements :strips :typing)

(:types 
	support - object
	disk peg - support
)

(:predicates 
	(on ?x - disk ?y - support)
	(clear ?s - support)
	(smaller ?x - disk ?y - support)
)

(:action move
	:parameters (
		?d - disk
		?from - support
		?to - support
	)
	:precondition 
		(and (on ?d ?from) (clear ?d) (clear ?to) (smaller ?d ?to)
	)
	:effect
		(and (not (on ?d ?from)) (on ?d ?to) (clear ?from) (not (clear ?to))
	)
)

)
