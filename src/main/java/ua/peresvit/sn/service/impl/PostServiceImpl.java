package ua.peresvit.sn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.peresvit.sn.config.Constant;
import ua.peresvit.sn.domain.dao.PostRepository;
import ua.peresvit.sn.domain.entity.Post;
import ua.peresvit.sn.service.PostService;

import java.util.List;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostRepository postRepository;

    @Override
    public <S extends Post> S save(S arg0) { return postRepository.save(arg0); }
    @Override
    public Post findOne(Long arg0) {

        Post post =  postRepository.findOne(arg0);
        return post;
    }
    @Override
    public List<Post> findAll() {return postRepository.findAll(); }

    @Override
    public Page<Post> findAll(Pageable pageable) {return postRepository.findAll(pageable); }

    @Override
    public String saveFile(Post post, MultipartFile inputFile) {

        //Files
        if(!inputFile.getOriginalFilename().isEmpty()){

            String fileContentType = inputFile.getContentType();
            String pathFile = Constant.UPLOAD_PATH + "/" + Constant.POST + "/" + fileContentType;

            String fileURL = Constant.uploadingFile(inputFile, pathFile);

            // don't delete any file,
            // this path could use other post

//            // delete old file
//            if(post.getPostId() != null){
//                String oldPath = post.getUrl();
//                if(!fileURL.equals(oldPath))
//                    Constant.deleteFile(oldPath);
//            }

            return fileURL;
        }

        return null;
    }

    @Override
    public void deleteOne(Long id) {
        if (postRepository.exists(id)) {
            postRepository.delete(id);
        }
    }
}
