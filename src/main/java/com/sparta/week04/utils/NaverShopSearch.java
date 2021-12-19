package com.sparta.week04.utils;

import com.sparta.week04.models.ItemDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class NaverShopSearch {
    public String search(String query) {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", "lgB9ChgVRdSydgQ4znGn");
        headers.add("X-Naver-Client-Secret", "mRV0t2BZr1");
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity = rest.exchange("https://openapi.naver.com/v1/search/shop.json?query=" + query, HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String response = responseEntity.getBody();
        System.out.println("Response status: " + status);
        System.out.println(response);
        return response;
    }

        public List<ItemDto> fromJSONtoItems(String result){
            //JSON오브젝트가 모여서 JSON어레이가 됨
            JSONObject rjson = new JSONObject(result); // 검색어로 찾은 값을 result로 받아와서 rjson으로 선언
            JSONArray items = rjson.getJSONArray("items"); // rjson에서 items를 키값으로 가지는 리스트(items)를 가져온다

            List<ItemDto> itemDtoList = new ArrayList<>(); // itemDto를 담아줄 리스트 선언

            for(int i = 0; i < items.length(); i++){ // items 안에 들어있는 것들을 하나씩 빼올 것것
                JSONObject itemJson = items.getJSONObject(i); //items에서 Json오브젝트를 꺼낸다 (i번째)
                ItemDto itemDto = new ItemDto(itemJson);
                itemDtoList.add(itemDto);
            }
            return itemDtoList;
        }
}