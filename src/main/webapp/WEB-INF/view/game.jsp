<%@ page import="com.gameofdice.*,com.gameofdice.games.*"%>
<%@  taglib  prefix="c"   uri="http://java.sun.com/jsp/jstl/core"  %>

<%String cPath = request.getContextPath();%>

<html>
    <head>
        <title>Game</title>
        <link type="text/css" rel="stylesheet" href="<%=cPath%>/css/game.css" />
    </head>
    <body>
        <img  id="background" src="<%=cPath%>/img/login_background.png"/>

        <c:if test="${not empty currentGame}">
            <div class="comp" id="player-score">
                <p>
                    Player dice : <c:out value="${currentGame.playerDice1}"/> x <c:out value="${currentGame.playerDice2}"/><br>
                    Score : <c:out value="${currentGame.getPlayerScore()}"/>
                </p>
            </div>

            <div class="comp" id="npc-score">
                <p>
                    NPC dice : <c:out value="${currentGame.npcDice1}"/> x <c:out value="${currentGame.npcDice2}"/><br>
                    Score : <c:out value="${currentGame.getNPCScore()}"/>
                </p>
            </div>

            <div class="comp" id="winner">
                <p>
                    The Winner is : <c:out value="${currentGame.getWinnerName()}"/>
                </p>
            </div>
            <!-- Dice sound -->
            <div>
                <audio autoplay hidden>
                    <source src="<%=cPath%>/audio/dice1.mp3" type="audio/mpeg">
                    If you're reading this, audio isn't supported. 
                </audio>
            </div>
        </c:if>

        <c:if test="${not empty lastGame}">
            <div class="comp" id="history">
                <p>Last Game Score 
                    <br>
                    Winner : <c:out value="${lastGame.getWinnerName()}"/>
                    <br>
                    Player dice : <c:out value="${lastGame.playerDice1}"/> x <c:out value="${lastGame.playerDice2}"/>
                    Score : <c:out value="${lastGame.getPlayerScore()}"/><br>
                    NPC dice : <c:out value="${lastGame.npcDice1}"/> x <c:out value="${lastGame.npcDice2}"/>
                    Score : <c:out value="${lastGame.getNPCScore()}"/>
                </p>
            </div>
        </c:if>

        <div class="comp" id="start-game">
            <form method="POST" action="">
                <button name="newGame">
                    <p>
                        <c:choose>
                            <c:when test="${currentGame == null}">
                                Play
                            </c:when>
                            <c:otherwise>
                                Try Again
                            </c:otherwise>
                        </c:choose>
                    </p>
                    <img src="<%=cPath%>/img/play.gif">
                </button>
            </form>
        </div>
    </body>
</html>