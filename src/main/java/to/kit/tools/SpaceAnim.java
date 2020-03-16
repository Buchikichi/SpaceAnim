package to.kit.tools;

import java.io.File;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SpaceAnim implements CommandLineRunner {
	private ImageWriter getGifImageWriter() {
		Iterator<ImageWriter> it = ImageIO.getImageWritersByFormatName("gif");

		return it.hasNext() ? it.next() : null;
	}

	@Override
	public void run(String... args) throws Exception {
		Space img = new Space();
		File file = new File("space_horizontal.gif");
		ImageWriter writer = getGifImageWriter();

		IIOMetadata meta = writer.getDefaultImageMetadata(ImageTypeSpecifier.createFromRenderedImage(img), null);
		String format = meta.getNativeMetadataFormatName();
		IIOMetadataNode root = (IIOMetadataNode) meta.getAsTree(format);

		IIOMetadataNode list = new IIOMetadataNode("ApplicationExtensions");
		IIOMetadataNode appNode = new IIOMetadataNode("ApplicationExtension");
		appNode.setAttribute("applicationID", "NETSCAPE");
		appNode.setAttribute("authenticationCode", "2.0");
		appNode.setUserObject(new byte[]{ 0x01, 0, 0 });
		list.appendChild(appNode);
		root.appendChild(list);
		meta.setFromTree(format, root);
		try (ImageOutputStream out = ImageIO.createImageOutputStream(file)) {
			writer.setOutput(out);
			writer.prepareWriteSequence(null);
			for (int cnt = 0; cnt < Space.IMAGE_SIZE; cnt++) {
				img.next();
				writer.writeToSequence(new IIOImage(img, null, cnt == 0 ? meta : null), null);
//				System.out.println("cnt:" + cnt);
			}
			writer.endWriteSequence();
		}
		System.out.println("hello!!");
	}
}
