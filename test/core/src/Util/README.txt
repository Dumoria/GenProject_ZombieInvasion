Id classes:
    Chaque instance d'une classe possède un id unique. Cet id commence par le numéro de l'instance courante
    suivit d'un chiffre représentant la classe de l'objet:
        - 1 pour les heros
        - 2 pour les ennemis
        - 3 pour les bonus

        (Voir si nec mais)
        - 4 pour les tires réussis
        - 5 pour une mort

    Pour récupérer ce chiffre, faire id % 10