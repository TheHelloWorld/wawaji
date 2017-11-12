package com.lzg.wawaji.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.util.StreamUtils;

/**
 * 声明@ResponseBody时，
 * <p>
 * Spring会通过AnnotationMethodHandlerAdapter去寻找对应的HttpMessageConverter,
 * <p>
 * 如果声明返回的类型是String，于是对应StringHttpMessageConverter。
 * <p>
 * 比较不幸的是，StringHttpMessageConverter所使用的默认字符集是ISO-8859-1
 * <p>
 * 堂堂Spring，竟然还在其中用西欧字符集作为其默认编码！
 * <p>
 * 由于StringHttpMessageConverter中的默认字符集变量声明为final，
 * <p>
 * 无法直接通过继承去覆盖，那就把StringHttpMessageConverter照抄一遍，
 * <p>
 * 通过构造方法注入UTF-8。
 * <p>
 *
 * @author tao.zhang
 * @version [V1.0, 2014-8-29]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Utf8StringHttpMessageConverter extends AbstractHttpMessageConverter<String> {

	private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	private final Charset defaultCharset;

	private final List<Charset> availableCharsets;

	private boolean writeAcceptCharset = true;


	public Utf8StringHttpMessageConverter() {
		this(DEFAULT_CHARSET);
	}

	public Utf8StringHttpMessageConverter(Charset defaultCharset) {
		super(new MediaType("text", "plain", defaultCharset), MediaType.ALL);
		this.defaultCharset = defaultCharset;
		this.availableCharsets = new ArrayList<Charset>(Charset.availableCharsets().values());
	}

	/**
	 * Indicates whether the {@code Accept-Charset} should be written to any outgoing request.
	 * <p>Default is {@code true}.
	 */
	public void setWriteAcceptCharset(boolean writeAcceptCharset) {
		this.writeAcceptCharset = writeAcceptCharset;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return String.class.equals(clazz);
	}

	@Override
	protected String readInternal(Class<? extends String> clazz, HttpInputMessage inputMessage) throws IOException {
		Charset charset = getContentTypeCharset(inputMessage.getHeaders().getContentType());
		return StreamUtils.copyToString(inputMessage.getBody(), charset);
	}

	@Override
	protected Long getContentLength(String s, MediaType contentType) {
		Charset charset = getContentTypeCharset(contentType);
		try {
			return (long) s.getBytes(charset.name()).length;
		} catch (UnsupportedEncodingException ex) {
			// should not occur
			throw new IllegalStateException(ex);
		}
	}

	@Override
	protected void writeInternal(String s, HttpOutputMessage outputMessage) throws IOException {
		if (this.writeAcceptCharset) {
			outputMessage.getHeaders().setAcceptCharset(getAcceptedCharsets());
		}
		Charset charset = getContentTypeCharset(outputMessage.getHeaders().getContentType());
		StreamUtils.copy(s, charset, outputMessage.getBody());
	}

	/**
	 * Return the list of supported {@link Charset}.
	 * <p>By default, returns {@link Charset#availableCharsets()}. Can be overridden in subclasses.
	 *
	 * @return the list of accepted charsets
	 */
	protected List<Charset> getAcceptedCharsets() {
		return this.availableCharsets;
	}

	private Charset getContentTypeCharset(MediaType contentType) {
		if (contentType != null && contentType.getCharSet() != null) {
			return contentType.getCharSet();
		} else {
			return this.defaultCharset;
		}
	}
}
