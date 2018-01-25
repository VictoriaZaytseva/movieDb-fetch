package models

case class TVShowResponse(page: Int,
                          total_results: Int,
                          total_pages: Int,
                          results: List[TVShow])
