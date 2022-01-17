package com.icia.board.service;

import com.icia.board.common.PagingConst;
import com.icia.board.dto.BoardDetailDTO;
import com.icia.board.dto.BoardPagingDTO;
import com.icia.board.dto.BoardSaveDTO;
import com.icia.board.dto.BoardUpdateDTO;
import com.icia.board.entity.BoardEntity;
import com.icia.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository br;

    @Override
    public Long save(BoardSaveDTO boardSaveDTO) {

        BoardEntity boardEntity = BoardEntity.saveBoard(boardSaveDTO);

        return br.save(boardEntity).getId();
    }

    @Override
    public List<BoardDetailDTO> findAll() {
        List<BoardEntity> boardEntityList = br.findAll();
        List<BoardDetailDTO> boardList = new ArrayList<>();

        for(BoardEntity b : boardEntityList){
            boardList.add(BoardDetailDTO.toBoardDetailDTO(b));
        }
        return boardList;
    }

    @Override
    public BoardDetailDTO findById(Long boardId) {
        Optional<BoardEntity> optionalBoardEntity = br.findById(boardId);
        /*
            optional 객체 메서드
            1. isPresent(): 데이터가 있으면 true, 없으면 false 반환
            2. isEmpty(): 데이터가 없으면 true, 있으면 false 반환
            3. get(): Optional 객체에 들어와있는 실제 데이터를 가져올 때
         */
        /*if(optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            boardDetailDTO = BoardDetailDTO.toBoardDetailDTO(boardEntity);
        }
        return BoardDetailDTO;*/

        Optional<BoardEntity> boardEntityOptional = br.findById(boardId);
        BoardEntity boardEntity = boardEntityOptional.get();
        BoardDetailDTO boardDetailDTO = BoardDetailDTO.toBoardDetailDTO(boardEntity);

        return boardDetailDTO;
    }

    @Override
    public void deleteById(Long boardId) {
        br.deleteById(boardId);
    }

   /* @Override
    public Long update(BoardDetailDTO boardDetailDTO) {

        BoardEntity boardEntity = BoardEntity.toUpdateBoard(boardDetailDTO);
        Long boardId = br.save(boardEntity).getId();
        return boardId;
    }*/

    @Override
    public Long update(BoardUpdateDTO boardUpdateDTO) {

        BoardEntity boardEntity = BoardEntity.toUpdateBoard(boardUpdateDTO);

        return br.save(boardEntity).getId();
    }

    @Override
    public Page<BoardPagingDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber();
        // 요청한 페이지가 1이면 페이지값을 0으로 하고 1이 아니면 요청 페이지에서 1을 뺀다.

        // page = page-1;
        page=(page==1)? 0:(page-1);
        // PageRequest=> 페이지요청 / page => 몇번째? / PagingConst.PAGE_LIMIT => 몇개씩?
        // Sort.by(Sort.Direction.DESC,"id") => 어떤식으로 볼거고 어떤걸 기준으로("id"는 Entity필드 이름으로 와야한다.)
        Page<BoardEntity> boardEntities =
                br.findAll(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));

        // Page<BoardEntity> => Page<BoardPagingDTO>
        // 기존 방식대로하면 안된다. -> 페이지 객체가 제공하는 메서드드를 못 쓴다! 이렇게 단순하게 옮기면
        //map(): 엔티티가 담긴 페이지 객체를 dto가 담긴 페이지객체로 변환해주는 역할
        Page<BoardPagingDTO> boardList = boardEntities.map(
                board -> new BoardPagingDTO(board.getId(),
                        board.getBoardWriter(),
                        board.getBoardTitle())
        );
        return boardList;
    }


}
