package dev.team4.portfoliotracker.services;

public class NewsService {
    //    @Autowired
//    private NewsRepository newsRepository;
//
//    @Autowired
//    News news;
//
//    @Autowired
//    NewsApiResponse newsApiResponse;

    //    public NewsApiResponse getNewsApiResponseObject(){
//        String targetUrl = "https://newsapi.org/v2/everything?q=stocks&from=2021-08-09&to=2021-08-09&sortBy=popularity&domains=forbes.com&apiKey=" + newsApiKey;
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(targetUrl))
//                .header("Accept","application/json")
//                .build();
//
//        HttpResponse<String> response = null;
//        try {
//            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        ObjectMapper objectMapper2 = new ObjectMapper();
//        try {
//            assert response != null;
//            return objectMapper2.readValue(response.body(), NewsApiResponse.class);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

//    public List<News> getDailyNews(){
//        return apiRepository.findAllNews();
//    }

}
