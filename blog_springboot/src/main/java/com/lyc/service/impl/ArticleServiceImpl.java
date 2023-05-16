package com.lyc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.constant.CommonConstant;
import com.lyc.constant.ParamConstant;
import com.lyc.handler.ServiceException;
import com.lyc.mapper.*;
import com.lyc.model.dto.ArticleDTO;
import com.lyc.model.dto.ConditionDTO;
import com.lyc.model.dto.TopDTO;
import com.lyc.model.po.*;
import com.lyc.model.vo.*;
import com.lyc.common.PageResult;
import com.lyc.service.ArticleService;
import com.lyc.service.RedisService;
import com.lyc.strategy.context.UploadStrategyContext;
import com.lyc.utils.FileUtils;
import com.lyc.utils.PageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.lyc.constant.CommonConstant.FALSE;
import static com.lyc.constant.RedisConstant.ARTICLE_LIKE_COUNT;
import static com.lyc.constant.RedisConstant.ARTICLE_VIEW_COUNT;
import static com.lyc.constant.CommonConstant.UPLOAD_FAIL;
import static com.lyc.enums.ArticleStatusEnum.PUBLIC;
import static com.lyc.enums.FilePathEnum.*;

/**
* @author 蜡笔
* @description 针对表【t_article】的数据库操作Service实现
* @createDate 2023-04-24 21:06:36
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService{
    @Resource
    private ArticleMapper articleMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UploadStrategyContext uploadStrategyContext;

    @Resource
    private BlogFileMapper blogFileMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private TagMapper tagMapper;

    @Resource
    private ArticleTagMapper articleTagMapper;

    @Override
    public PageResult<ArticleBackVO> getBackArticleList(ConditionDTO condition) {
        //查询文章数据量
        Long count=articleMapper.ArticleCount(condition);
        //没有文章直接返回
        if(count==0){
            return new PageResult<>();
        }

        //查询文章基本信息
        List<ArticleBackVO> articleBackVOS = articleMapper
                .selectArticleBackVo(PageUtils.getLimit(), PageUtils.getSize(),condition);
        //文章浏览量
        Map<Object, Double> viewCountMap = redisService.getZsetAllScore(ARTICLE_VIEW_COUNT);
        //文章点赞量
        Map<String, Integer> likeCountMap = redisService.getHashAll(ARTICLE_LIKE_COUNT);
        //封装数据
        articleBackVOS.forEach(item ->{
            Double viewCount = Optional.ofNullable(viewCountMap.get(item.getId())).orElse((double) 0);
            item.setViewCount(viewCount.intValue());
            Integer likeCount = likeCountMap.get(item.getId().toString());
            item.setLikeCount(Optional.ofNullable(likeCount).orElse(0));
        });

        return new PageResult<>(articleBackVOS,count);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String uploadPicture(MultipartFile file) {
        //上传文件,并返回文件的网络上传路径
        String url = uploadStrategyContext.executeUploadStrategy(file, ARTICLE.getPath());

        //获取文件名，后缀
        String md5 = FileUtils.getMd5(file);
        String extension = FileUtils.getExtension(file);
        BlogFile blogFile = blogFileMapper.selectOne(new LambdaQueryWrapper<BlogFile>()
                .select(BlogFile::getId)
                .eq(StringUtils.isNotEmpty(md5), BlogFile::getFileName, md5)
                .eq(BlogFile::getFilePath, ARTICLE.getPath()));

        //文件信息保存到数据库中
        if(Objects.isNull(blogFile)){
            BlogFile oneBlogFile = BlogFile.builder()
                    .fileName(md5)
                    .filePath(ARTICLE.getPath())
                    .extendName(extension)
                    .fileSize((int) file.getSize())
                    .fileUrl(url)
                    .build();
            int count = blogFileMapper.insert(oneBlogFile);

            //文件上传失败
            if(count==0){
                throw new RuntimeException(UPLOAD_FAIL);
            }
        }

        return url;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addArticle(ArticleDTO articleDTO) {
        //封文章对象
        Article article = new Article();
        //拷贝部分对象属性
        BeanUtils.copyProperties(articleDTO,article);
        //手动添加部分对象属性
        article.setUserId(ParamConstant.ADMIN_ID);
        Category category = categoryMapper.selectOne(new LambdaQueryWrapper<Category>()
                .select(Category::getId)
                .eq(Category::getCategoryName, articleDTO.getCategoryName()));
        article.setCategoryId(category.getId());
        //插入到数据库
        int articleCount = articleMapper.insert(article);
        if(articleCount==0){
            throw new RuntimeException("文章添加失败");
        }

        //封装文章标签关系表
        List<String> tagNameList = articleDTO.getTagNameList();
        for(String tagName:tagNameList){
            Tag tag = tagMapper.selectOne(new LambdaQueryWrapper<Tag>()
                    .select(Tag::getId)
                    .eq(Tag::getTagName, tagName));
            //插入文章标签关系表
            articleTagMapper.insert(ArticleTag.builder()
                    .articleId(article.getId())
                    .tagId(tag.getId())
                    .build());
        }

    }

    @Override
    public PageResult<ArticleHomeVO> listArticleHomeVO() {
        // 查询文章数量
        Long count = articleMapper.selectCount(new LambdaQueryWrapper<Article>()
                .eq(Article::getIsDelete, FALSE)
                .eq(Article::getStatus, PUBLIC.getStatus()));
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询首页文章
        List<ArticleHomeVO> articleHomeVOList = articleMapper.selectArticleHomeList(PageUtils.getLimit(), PageUtils.getSize());
        return new PageResult<>(articleHomeVOList, count);
    }

    @Override
    public List<ArticleRecommendVO> listArticleRecommendVO() {
        return articleMapper.selectArticleRecommend();
    }

    @Override
    public ArticleVO getArticleDetail(Integer articleId) {
        //本文
        ArticleVO articleVO = articleMapper.selectArticleHomeById(articleId);
        if (Objects.isNull(articleVO)){
            return null;
        }
        // 浏览量+1
        redisService.incrZet(ARTICLE_VIEW_COUNT, articleId, 1D);
        // 查询浏览量
        Double viewCount = Optional.ofNullable(redisService.getZsetScore(ARTICLE_VIEW_COUNT, articleId))
                .orElse((double) 0);
        articleVO.setViewCount(viewCount.intValue());
        // 查询点赞量
        Integer likeCount = redisService.getHash(ARTICLE_LIKE_COUNT, articleId.toString());
        articleVO.setLikeCount(Optional.ofNullable(likeCount).orElse(0));

        //下一篇文章
        ArticlePaginationVO nextArticle = articleMapper.selectArticlePaginationVO("lt",articleId);
        articleVO.setNextArticle(nextArticle);
        //上一篇文章
        ArticlePaginationVO lastArticle = articleMapper.selectArticlePaginationVO("gt",articleId);
        articleVO.setLastArticle(lastArticle);

        return articleVO;
    }

    @Override
    public void changeTop(TopDTO topDTO) {
        Integer id = topDTO.getId();
        Article article = articleMapper.selectById(id);
        Integer isTop = topDTO.getIsTop();
        article.setIsTop(isTop);
        int count = articleMapper.updateById(article);
        if(count==0){
            throw new ServiceException(isTop==0?"置顶失败！":"取消置顶失败！");
        }
    }

    @Override
    public ArticleInfoVO getArticleInfo(Integer articleId) {
        ArticleInfoVO articleInfoVO=articleMapper.selectArticleInfo(articleId);
        return articleInfoVO;
    }
}




