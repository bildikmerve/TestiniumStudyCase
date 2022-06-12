package com.testinium.webservice.Scenarios;

import com.google.gson.Gson;
import com.testinium.webservice.Base.BaseFunction;
import com.testinium.webservice.Models.Board;
import com.testinium.webservice.Models.BoardList;
import com.testinium.webservice.Models.Card;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class TrelloBoard extends  BaseFunction{
    private  String apiKey = "7bf559540623ca90988ccabf2ba26026";
    private  String apiToken = "6c6d8738a5ab084c38ca75e0804c632ea71cc45700e231ecd6bab39414ea1f5e";

    @Before
    public void setApi() {
        setAPIKey(apiKey);
        setAPIToken(apiToken);
    }

    @Test
    public void Test() {
        // Create a new Board
        Map <String, Object> boardData = new HashMap<String, Object>();
        boardData.put("name", "board");
        var boardCreate = Post("https://api.trello.com/1/boards/", boardData);
        Board board = new Gson().fromJson(boardCreate.getBody(), Board.class);
        Assert.assertEquals(200, boardCreate.getStatus());

        // Create a new List
        Map <String, Object> listData = new HashMap<String, Object>();
        listData.put("name", "list");
        listData.put("idBoard",  board.getId());
        var listCreate = Post("https://api.trello.com/1/lists", listData);
        BoardList boardList = new Gson().fromJson(listCreate.getBody(), BoardList.class);

        String[] cardIds = new  String[2];
        // Create a new Card
        Map <String, Object> cardData1 = new HashMap<String, Object>();
        cardData1.put("name", "card1");
        cardData1.put("idList",  boardList.getId());
        var createFirstCard = Post("https://api.trello.com/1/cards",cardData1);
        Assert.assertEquals(200, createFirstCard.getStatus());
        Card card1 = new Gson().fromJson(createFirstCard.getBody(), Card.class);
        cardIds[0] = card1.getId();


        // Create a new Card
        Map <String, Object> cardData2 = new HashMap<String, Object>();
        cardData2.put("name", "card2");
        cardData2.put("idList",  boardList.getId());
        var createSecondCard = Post("https://api.trello.com/1/cards",cardData2);
        Assert.assertEquals(200, createSecondCard.getStatus());
        Card card2 = new Gson().fromJson(createSecondCard.getBody(), Card.class);
        cardIds[1] = card2.getId();

        // Randomly Card update
        Random rnd = new Random();
        Map <String, Object> updateCardData = new HashMap<String, Object>();
        updateCardData.put("name", "updateCard");
        var randomCardId = cardIds[rnd.nextInt(cardIds.length - 1)];
        var updateCard = Put("https://api.trello.com/1/cards/" , randomCardId,updateCardData);
        Assert.assertEquals(200, updateCard.getStatus());

        // Delete first Card
        var deleteFirstCard = Delete("https://api.trello.com/1/cards/", cardIds[0]);
        Assert.assertEquals(200, deleteFirstCard.getStatus());

        // Delete second Card
        var deleteSecondCard = Delete("https://api.trello.com/1/cards/", cardIds[1]);
        Assert.assertEquals(200, deleteSecondCard.getStatus());

        // Delete second Card
        var deleteBoard = Delete("https://api.trello.com/1/boards/", board.getId());
        Assert.assertEquals(200, deleteBoard.getStatus());
    }

}
