@test
def testPatternList01(): Bool = match Nil with {
    case Nil => true
    case _   => false
}

@test
def testPatternList02(): Bool = match Nil with {
    case Nil      => true
    case 1 :: Nil => false
    case _        => false
}

@test
def testPatternList03(): Bool = match Nil with {
    case Nil           => true
    case 1 :: Nil      => false
    case 1 :: 2 :: Nil => false
    case _             => false
}

@test
def testPatternList04(): Bool = match 1 :: Nil with {
    case Nil      => false
    case 1 :: Nil => true
    case _        => false
}

@test
def testPatternList05(): Bool = match 1 :: 2 :: Nil with {
    case Nil      => false
    case x :: xs  => true
}

@test
def testPatternList06(): Bool = match 1 :: 2 :: Nil with {
    case Nil            => false
    case 1 :: Nil       => false
    case 1 :: 2 :: Nil  => true
    case _              => false
}

@test
def testPatternList07(): Bool = match 1 :: 2 :: Nil with {
    case x :: y :: Nil  => x + 1 == y
    case _              => false
}

@test
def testPatternList08(): Bool = match 1 :: 2 :: 3 :: Nil with {
    case x :: 2 :: z :: Nil => x + 2 == z
    case _                  => false
}


@test
def testPatternOption01(): Bool = match Nil with {
    case Nil => true
    case _   => false
}

@test
def testPatternOption02(): Bool = match None :: Nil with {
    case Nil         => false
    case None :: Nil => true
    case _           => false
}

@test
def testPatternOption03(): Bool = match Some(123) :: Nil with {
    case Nil              => false
    case Some(x) :: Nil   => x == 123
    case _                => false
}


@test
def testListList01(): Bool = match Nil : List[List[Int]] with {
    case Nil              => true
    case _                => false
}

@test
def testListList02(): Bool = match (1 :: Nil) :: Nil with {
    case Nil                => false
    case (1 :: Nil) :: Nil  => true
    case _                  => false
}

@test
def testListList03(): Bool = match (1 :: Nil) :: (2 :: Nil) :: Nil with {
    case Nil                             => false
    case (x :: Nil) :: (y :: Nil) :: Nil => x + 1 == y
    case _                               => false
}
