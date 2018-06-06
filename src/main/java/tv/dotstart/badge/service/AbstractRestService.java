package tv.dotstart.badge.service;

import java.io.FileNotFoundException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Provides an abstract REST service base which provides simple methods for accessing remote APIs.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
public abstract class AbstractRestService {

  private final RestTemplate rest = new RestTemplate();

  /**
   * Extends the headers which are to be sent along with every single request.
   *
   * @param headers a map of headers and their respective values.
   */
  protected void customizeHeaders(@NonNull MultiValueMap<String, String> headers) {
  }

  /**
   * Translates a passed endpoint URL (for instance to prepend a configured base URL or append
   * authentication information).
   *
   * @param endpointUrl an endpoint URL.
   * @return a translated endpoint URL.
   */
  @NonNull
  protected String translateUrl(@NonNull String endpointUrl) {
    return endpointUrl;
  }

  /**
   * Performs an HTTP get request on an arbitrary endpoint.
   *
   * @param endpoint an endpoint (relative to the API base URL).
   * @param type a response type.
   * @param urlVariables an array of values with which URL variables will be replaced.
   * @param <E> a response type.
   * @return a parsed response.
   * @throws HttpClientErrorException when the request completes with an invalid response.
   * @throws FileNotFoundException when the request completes with a "Not Found" error.
   */
  @NonNull
  protected <E> E get(@NonNull String endpoint, @NonNull Class<E> type, String... urlVariables)
      throws FileNotFoundException {
    var headers = new LinkedMultiValueMap<String, String>();
    this.customizeHeaders(headers);

    try {
      return this.rest
          .exchange(this.translateUrl(endpoint), HttpMethod.GET, new HttpEntity<>(headers), type,
              (Object[]) urlVariables).getBody();
    } catch (HttpClientErrorException ex) {
      if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
        throw new FileNotFoundException("No such object: " + endpoint);
      }

      throw ex;
    }
  }
}
