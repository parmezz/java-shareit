package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.dto.CommentInputDto;
import ru.practicum.shareit.item.dto.ItemFullDto;
import ru.practicum.shareit.item.dto.ItemInputDto;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ItemFullDto create(@RequestHeader("X-Sharer-User-Id") Long userId,
                              @Valid @RequestBody ItemInputDto itemInputDto) {
        log.info("Получен POST-запрос к эндпоинту: '/items' на добавление вещи владельцем с ID={}", userId);
        return itemService.create(userId, itemInputDto);
    }

    @PatchMapping("/{itemId}")
    public ItemFullDto update(@RequestHeader("X-Sharer-User-Id") Long userId,
                              @RequestBody ItemInputDto itemInputDto,
                              @PathVariable Long itemId) {
        log.info("Получен PATCH-запрос к эндпоинту: '/items' на обновление вещи с ID={}", itemId);
        return itemService.update(userId, itemId, itemInputDto);
    }

    @GetMapping("/{itemId}")
    public ItemFullDto get(@RequestHeader("X-Sharer-User-Id") Long userId,
                           @PathVariable Long itemId) {
        log.info("Получен GET-запрос к эндпоинту: '/items' на получение вещи с ID={}", userId);
        return itemService.getById(userId, itemId);
    }

    @GetMapping
    public List<ItemFullDto> getByUserId(@RequestHeader("X-Sharer-User-Id") Long userId,
                                         @RequestParam(defaultValue = "0") Integer from,
                                         @RequestParam(defaultValue = "10") Integer size) {
        log.info("Получен GET-запрос к эндпоинту: '/items' на получение вещи юзера с ID={}", userId);
        return itemService.getByUserId(userId, from, size);
    }

    @GetMapping("/search")
    public List<ItemFullDto> search(@RequestParam String text,
                                    @RequestParam(defaultValue = "0") Integer from,
                                    @RequestParam(defaultValue = "10") Integer size) {
        log.info("Получен GET-запрос к эндпоинту: '/items/search' на поиск вещи с текстом={}", text);
        return itemService.search(text, from, size);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(@RequestHeader("X-Sharer-User-Id") Long userId,
                                 @PathVariable Long itemId,
                                 @Valid @RequestBody CommentInputDto commentInputDto) {
        log.info("Получен POST-запрос к эндпоинту: '/items/comment' на" +
                " добавление отзыва пользователем с ID={}", userId);
        return itemService.addComment(userId, itemId, commentInputDto);
    }
}