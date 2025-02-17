PGDMP                  	    {         	   challenge    16.0    16.0                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            	           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            
           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    16460 	   challenge    DATABASE     �   CREATE DATABASE challenge WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';
    DROP DATABASE challenge;
                postgres    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
                pg_database_owner    false                       0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                   pg_database_owner    false    4            �            1259    16736    merchant    TABLE     �   CREATE TABLE public.merchant (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    merchant_name character varying(100) NOT NULL,
    merchant_location character varying(255) NOT NULL,
    open boolean NOT NULL
);
    DROP TABLE public.merchant;
       public         heap    postgres    false    4            �            1259    16764    order_detail    TABLE     �   CREATE TABLE public.order_detail (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    user_order_id uuid NOT NULL,
    product_id uuid NOT NULL,
    quantity integer NOT NULL,
    total_price numeric(20,2) NOT NULL
);
     DROP TABLE public.order_detail;
       public         heap    postgres    false    4            �            1259    16753    product    TABLE     �   CREATE TABLE public.product (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    product_name character varying(150) NOT NULL,
    price numeric(12,2) NOT NULL,
    merchant_id uuid NOT NULL
);
    DROP TABLE public.product;
       public         heap    postgres    false    4            �            1259    16742 
   user_order    TABLE     �   CREATE TABLE public.user_order (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    order_time timestamp without time zone NOT NULL,
    destination_address character varying(255) NOT NULL,
    user_id uuid NOT NULL,
    completed boolean NOT NULL
);
    DROP TABLE public.user_order;
       public         heap    postgres    false    4            �            1259    16730    users    TABLE     �   CREATE TABLE public.users (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    username character varying(100) NOT NULL,
    email_address character varying(100) NOT NULL,
    password character varying(100) NOT NULL
);
    DROP TABLE public.users;
       public         heap    postgres    false    4                      0    16736    merchant 
   TABLE DATA           N   COPY public.merchant (id, merchant_name, merchant_location, open) FROM stdin;
    public          postgres    false    216   �                 0    16764    order_detail 
   TABLE DATA           \   COPY public.order_detail (id, user_order_id, product_id, quantity, total_price) FROM stdin;
    public          postgres    false    219   �                 0    16753    product 
   TABLE DATA           G   COPY public.product (id, product_name, price, merchant_id) FROM stdin;
    public          postgres    false    218   �                 0    16742 
   user_order 
   TABLE DATA           ]   COPY public.user_order (id, order_time, destination_address, user_id, completed) FROM stdin;
    public          postgres    false    217                    0    16730    users 
   TABLE DATA           F   COPY public.users (id, username, email_address, password) FROM stdin;
    public          postgres    false    215   3       g           2606    16741    merchant merchant_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.merchant
    ADD CONSTRAINT merchant_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.merchant DROP CONSTRAINT merchant_pkey;
       public            postgres    false    216            m           2606    16769    order_detail order_detail_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.order_detail
    ADD CONSTRAINT order_detail_pkey PRIMARY KEY (id);
 H   ALTER TABLE ONLY public.order_detail DROP CONSTRAINT order_detail_pkey;
       public            postgres    false    219            i           2606    16747    user_order order_pkey 
   CONSTRAINT     S   ALTER TABLE ONLY public.user_order
    ADD CONSTRAINT order_pkey PRIMARY KEY (id);
 ?   ALTER TABLE ONLY public.user_order DROP CONSTRAINT order_pkey;
       public            postgres    false    217            k           2606    16758    product product_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.product DROP CONSTRAINT product_pkey;
       public            postgres    false    218            e           2606    16735    users users_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    215            o           2606    16759    product merchant_id_constraint    FK CONSTRAINT     �   ALTER TABLE ONLY public.product
    ADD CONSTRAINT merchant_id_constraint FOREIGN KEY (merchant_id) REFERENCES public.merchant(id);
 H   ALTER TABLE ONLY public.product DROP CONSTRAINT merchant_id_constraint;
       public          postgres    false    216    218    4711            p           2606    16775 "   order_detail product_id_constraint    FK CONSTRAINT     �   ALTER TABLE ONLY public.order_detail
    ADD CONSTRAINT product_id_constraint FOREIGN KEY (product_id) REFERENCES public.product(id);
 L   ALTER TABLE ONLY public.order_detail DROP CONSTRAINT product_id_constraint;
       public          postgres    false    218    4715    219            n           2606    16748    user_order user_id_constraint    FK CONSTRAINT     |   ALTER TABLE ONLY public.user_order
    ADD CONSTRAINT user_id_constraint FOREIGN KEY (user_id) REFERENCES public.users(id);
 G   ALTER TABLE ONLY public.user_order DROP CONSTRAINT user_id_constraint;
       public          postgres    false    217    4709    215            q           2606    16770 %   order_detail user_order_id_constraint    FK CONSTRAINT     �   ALTER TABLE ONLY public.order_detail
    ADD CONSTRAINT user_order_id_constraint FOREIGN KEY (user_order_id) REFERENCES public.user_order(id);
 O   ALTER TABLE ONLY public.order_detail DROP CONSTRAINT user_order_id_constraint;
       public          postgres    false    4713    219    217                  x������ � �            x������ � �            x������ � �            x������ � �            x������ � �     