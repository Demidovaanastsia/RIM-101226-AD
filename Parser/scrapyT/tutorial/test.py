# from scrapy.spiders import CrawlSpider, Rule
# from scrapy.linkextractors import LinkExtractor
import scrapy


class CinemaSpider(scrapy.Spider):
    # Unique name of spider
    name = "cinema_spiders"

    # Start url for spider
    start_url = ['https://www.imdb.com/search/title/']

    # Доступные домены за которые не может выходить spider
    allowed_domains = ['imdb.com']

    # Правила определяющие ограничения на паука по поиску на страницах
    rule = (
        scrapy.spiders.Rule(scrapy.linkextractors.LinkExtractor(
            restrict_xpath
        )
        )
    )

    def parse(self, response):
        page = response.url.split("/")[-2]
        filename = f'quotes-{page}.html'
        with open(filename, 'wb') as f:
            f.write(response.body)
        self.log(f'Saved file {filename}')
